package com.github.dfengwei.apidoc.resolver.comment;

import static java.lang.Integer.valueOf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Group;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 接口名称（包含分组名称）解析器
 *
 * @author dfengwei@163.com
 */
public class ApiNameResolver {

    /**
     * 解析
     *
     * @param terminal 终端
     * @param api 接口明细
     */
    public static void resolve(Terminal terminal, Api api) {
        // 解析接口分组/名称，格式：@apiName {一级分组序号:一级分组名称} {二级分组序号:二级分组名称} ... 接口序号:接口名称
        List<String> commentList = ReUtil.findAll(Patterns.API_NAME, api.getComment(), 1);

        // 多行注释转单行并去除首尾空白字符
        commentList = commentList.stream().map(c -> ReUtil.replaceAll(c, Patterns.LINE_FEED, StrUtil.SPACE).trim())
            .collect(Collectors.toList());

        if (commentList.isEmpty()) {
            Logger.error("[接口解析]终端:【{}】, 存在缺失@apiName的接口, 跳过该接口文档的生成, 文件:【{}】", terminal.getName(),
                api.getSourceFilePath());
            return;
        } else if (commentList.size() > 1) {
            Logger.warn("[接口解析]终端:【{}】, 存在拥有多个@apiName的接口, 将使用首次出现的@apiName:【{}】, 文件:【{}】", terminal.getName(),
                commentList.get(0), api.getSourceFilePath());
        }
        String comment = commentList.get(0);

        // 分割注释（带中括号或大括号的，以括号分割，否则以空格分割）
        List<String> itemList = ReUtil.findAll(Patterns.SEPARATOR, comment, 0);

        // 有效性检查
        if (itemList.size() < 2) {
            Logger.error("[接口解析]终端:【{}】, @apiName:【{}】, 至少需要包含一个组名和一个接口名称", terminal.getName(), comment);
        }

        // 生成组列表
        List<Group> groupList = new ArrayList<>();

        StringBuilder apiNameWithSequence = new StringBuilder();
        for (String item : itemList) {
            List<String> itemContent = ReUtil.findAll(Patterns.BRACKET, item, 0);
            if (!itemContent.isEmpty()) {
                // 注释条目被括号包裹，是组名
                String groupSequenceAndName = ReUtil.findAll(Patterns.BRACKET, item, 0).get(0);
                // 提取分组序号和分组名称
                List<String> groupSequenceAndNameItemList = StrUtil.splitTrim(groupSequenceAndName, ":");
                int sequence = Integer.MAX_VALUE;
                String groupName;
                if (groupSequenceAndNameItemList.size() == 1) {
                    groupName = groupSequenceAndNameItemList.get(0).trim();
                    Logger.warn("[接口解析]终端:【{}】, @apiName:【{}】, 分组:【{}】未包含序号, 将排序至其父分组的尾部", terminal.getName(), comment,
                        groupName);
                } else {
                    sequence = Integer.parseInt(groupSequenceAndNameItemList.get(0));
                    groupName = groupSequenceAndNameItemList.get(1).trim();
                }
                Group group = new Group();
                group.setName(groupName);
                group.setSequence(sequence);
                groupList.add(group);
            } else {
                // 注释条目未被括号包裹，是接口名的一部分
                apiNameWithSequence.append(item).append(StrUtil.SPACE);
            }
        }

        // 提取接口序号和名称
        int apiSequence = Integer.MAX_VALUE;
        String apiName;
        List<String> apiNameSequenceAndNameItemList = StrUtil.splitTrim(apiNameWithSequence, ":");
        if (apiNameSequenceAndNameItemList.size() == 1) {
            apiName = apiNameSequenceAndNameItemList.get(0);
            Logger.warn("[接口解析]终端:【{}】, @apiName:【{}】, 接口名称未包含序号, 将排序至其所属分组的尾部", terminal.getName(), comment);
        } else {
            apiSequence = valueOf(apiNameSequenceAndNameItemList.get(0));
            apiName = apiNameSequenceAndNameItemList.get(1);
        }

        // 设置接口序号、名称和面包屑
        api.setSequence(apiSequence);
        api.setName(apiName);
        String groupBreadcrumb = groupList.stream().map(Group::getName).collect(Collectors.joining("→"));
        String breadcrumb = StrUtil.format("{}→{}", groupBreadcrumb, apiName);
        api.setBreadcrumb(breadcrumb);

        // 设置组列表
        api.setGroupList(groupList);
    }

}
