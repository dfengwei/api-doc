package com.github.dfengwei.apidoc.resolver.comment;

import java.util.*;
import java.util.stream.Collectors;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.RequestParam;
import com.github.dfengwei.apidoc.domain.Terminal;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 接口请求参数解析器
 *
 * @author dfengwei@163.com
 */
public class ApiParamResolver {

    /**
     * 解析
     *
     * @param terminal 终端
     * @param api 接口
     */
    public static void resolve(Terminal terminal, Api api) {
        // 解析接口请求参数，格式为：@apiParam {数据类型} [参数名称] 参数说明
        List<String> commentList = ReUtil.findAll(Patterns.API_PARAM, api.getComment(), 1);

        // 多行注释转单行并去除首尾空白字符
        commentList = commentList.stream().map(c -> ReUtil.replaceAll(c, Patterns.LINE_FEED, StrUtil.SPACE).trim())
            .collect(Collectors.toList());

        // 解析并生成ApiParam列表
        List<RequestParam> requestParamList = new ArrayList<>();

        // 无请求参数的情况
        if (CollUtil.isEmpty(commentList)) {
            api.setRequestParamList(requestParamList);
        }

        // 请求参数名称Map
        Map<String, RequestParam> paramMap = new LinkedHashMap<>();

        // 遍历@apiParam注释列表
        for (String comment : commentList) {
            // 分割注释（带中括号或大括号的，以括号分割，否则以空格分割）
            List<String> itemList = ReUtil.findAll(Patterns.SEPARATOR, comment, 0);

            // 有效性检查
            if (itemList.size() < 3) {
                Logger.error("[接口解析]终端:【{}】, 接口:【{}】, 请求参数格式错误, 注释内容:【{}】", terminal.getName(), api.getBreadcrumb(), comment);
                continue;
            }

            /* ---------- 参数名称 ---------- */

            String fullName;
            boolean required = true;
            List<String> itemContent = ReUtil.findAll(Patterns.BRACKET, itemList.get(1), 0);
            if (!itemContent.isEmpty()) {
                // 注释条目被中括号包裹，是非必填参数
                fullName = ReUtil.findAll(Patterns.SQUARE_BRACKET, itemList.get(1), 0).get(0).trim();
                required = false;
            } else {
                fullName = itemList.get(1);
            }

            /* ---------- 数据类型 ---------- */

            String dataType;
            itemContent = ReUtil.findAll(Patterns.CURLY_BRACKET, itemList.get(0), 0);
            if (!itemContent.isEmpty()) {
                dataType = ReUtil.findAll(Patterns.CURLY_BRACKET, itemList.get(0), 0).get(0).trim();
            } else {
                dataType = itemList.get(0);
                Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 请求参数:【{}】, 数据类型建议使用大括号包裹", terminal.getName(), api.getBreadcrumb(),
                    fullName);
            }

            /* ---------- 字段描述 ---------- */

            String description = itemList.subList(2, itemList.size()).stream().map(String::trim)
                .collect(Collectors.joining(StrUtil.SPACE));

            // 生成请求参数对象
            RequestParam requestParam = new RequestParam();
            requestParam.setFullName(fullName);
            requestParam.setName(StrUtil.blankToDefault(StrUtil.subAfter(fullName, StrUtil.DOT, true), fullName));
            requestParam.setDataType(dataType);
            requestParam.setRequired(required);
            requestParam.setComment(comment);

            /* ---------- 字段描述处理 ---------- */

            // 设置描述
            requestParam.setDescription(description);

            // 放入map
            if (!paramMap.containsKey(fullName)) {
                paramMap.put(fullName, requestParam);
            } else {
                Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 请求参数:【{}】, 参数名称重复, 将使用首次出现的@apiParam:【{}】", terminal.getName(),
                    api.getBreadcrumb(), fullName, paramMap.get(fullName).getComment());
            }
        }

        /* ---------- 父子参数有效性检查 ---------- */

        Set<String> dataTypeWarningParamSet = new HashSet<>();
        for (String fullName : paramMap.keySet()) {
            // 父字段名称
            String parentFullName = StrUtil.subBefore(fullName, ".", true);
            if (!StrUtil.equals(fullName, parentFullName)) {
                if (!paramMap.containsKey(parentFullName)) {
                    Logger.error("[接口解析]终端:【{}】, 接口:【{}】, 请求参数:【{}】, 无法找到父参数, 忽略", terminal.getName(), api.getBreadcrumb(),
                        fullName);
                } else {
                    RequestParam parentParam = paramMap.get(parentFullName);
                    if (!StrUtil.equalsAnyIgnoreCase(parentParam.getDataType(), "JSONObject")
                        && !StrUtil.equalsAnyIgnoreCase(parentParam.getDataType(), "JSONArray")) {
                        if (!dataTypeWarningParamSet.contains(parentParam.getFullName())) {
                            Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 请求参数:【{}】, 拥有子参数, 但数据类型不是JSONObject或SONArray",
                                terminal.getName(), api.getBreadcrumb(), parentParam.getFullName());
                            dataTypeWarningParamSet.add(parentFullName);
                        }
                    }
                }
            }
        }

        // 生成参数树列表
        api.setRequestParamList(generateRequestParamTreeList(paramMap));
    }

    /**
     * 生成请求参数树列表
     * 
     * @param requestParamMap 请求参数map
     * @return 请求参数树列表
     */
    private static List<RequestParam> generateRequestParamTreeList(Map<String, RequestParam> requestParamMap) {
        // 请求参数转列表
        List<RequestParam> requestParamList = new ArrayList<>(requestParamMap.values());

        // 请求参数树列表
        List<RequestParam> requestParamTreeList = new ArrayList<>();

        // 层级遍历
        for (int i = 0;; i++) {
            // 因为需要在遍历过程中删除元素，必须使用迭代器
            Iterator<RequestParam> iterator = requestParamList.iterator();

            // 如果迭代器没有后续元素了，表示层级遍历已完成，退出
            if (!iterator.hasNext()) {
                break;
            }

            // 如果迭代器还有后续元素
            while (iterator.hasNext()) {
                RequestParam requestParam = iterator.next();
                if (StrUtil.count(requestParam.getFullName(), ".") == i) {
                    // 全限定名中，点的数量等于当前层级
                    if (i == 0) {
                        // 当前元素为0层元素，直接放入请求参数树列表
                        requestParamTreeList.add(requestParam);
                    } else {
                        // 当前元素为非0层元素，找到其父参数，并放入其子元素列表中
                        String parentKey = StrUtil.subBefore(requestParam.getFullName(), ".", true);
                        RequestParam parentRequestParam = requestParamMap.get(parentKey);
                        if (parentRequestParam != null) {
                            // 父元素存在
                            if (parentRequestParam.getChildren() == null) {
                                parentRequestParam.setChildren(new ArrayList<>());
                            }
                            parentRequestParam.getChildren().add(requestParam);
                        }
                    }

                    // 移除当前元素
                    iterator.remove();
                }
            }
        }

        return requestParamTreeList;
    }

}
