package com.github.dfengwei.apidoc.resolver.comment;

import java.util.List;
import java.util.stream.Collectors;

import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 接口描述解析器
 *
 * @author dfengwei@163.com
 */
public class ApiDescriptionResolver {

    /**
     * 解析
     *
     * @param terminal 终端
     * @param api 接口
     */
    public static void resolve(Terminal terminal, Api api) {
        // 解析接口描述，格式为：@apiDescription xxx
        List<String> commentList = ReUtil.findAll(Patterns.API_DESCRIPTION, api.getComment(), 1);

        // 多行注释转单行并去除首尾空白字符
        commentList = commentList.stream().map(c -> ReUtil.replaceAll(c, Patterns.LINE_FEED, StrUtil.SPACE).trim())
            .collect(Collectors.toList());

        if (commentList.size() > 1) {
            Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 拥有多个@apiDescription, 将进行合并", terminal.getName(), api.getBreadcrumb());
        }

        StringBuilder description = new StringBuilder();
        for (String comment : commentList) {
            description.append(comment.trim()).append(StrUtil.SPACE);
        }

        api.setDescription(description.toString());
    }

}
