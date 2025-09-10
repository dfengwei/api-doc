package com.github.dfengwei.apidoc.resolver.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 接口地址解析器
 *
 * @author dfengwei@163.com
 */
public class ApiAddrResolver {

    /**
     * 解析
     *
     * @param terminal 终端
     * @param api 接口
     */
    public static void resolve(Terminal terminal, Api api) {
        // 解析接口地址，格式为：@apiAddr {请求方式1} {请求方式2} {请求方式n} URL
        List<String> commentList = ReUtil.findAll(Patterns.API_ADDR, api.getComment(), 1);

        // 多行注释转单行并去除首尾空白字符
        commentList = commentList.stream().map(c -> ReUtil.replaceAll(c, Patterns.LINE_FEED, StrUtil.SPACE).trim())
            .collect(Collectors.toList());
        if (commentList.isEmpty()) {
            Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 缺失@apiAddr", terminal.getName(), api.getBreadcrumb());
            return;
        } else if (commentList.size() > 1) {
            Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 拥有多个@apiAddr, 将使用首次出现的@apiAddr:【{}】", terminal.getName(), api.getBreadcrumb(),
                commentList.get(0));
        }
        String comment = commentList.get(0);

        // 分割注释（带中括号或大括号的，以括号分割，否则以空格分割）
        List<String> itemList = ReUtil.findAll(Patterns.SEPARATOR, comment, 0);

        // 提取请求方式和URL
        List<String> methodList = new ArrayList<>();
        StringBuilder url = new StringBuilder();
        for (String item : itemList) {
            List<String> itemContent = ReUtil.findAll(Patterns.BRACKET, item, 0);
            if (!itemContent.isEmpty()) {
                // 注释条目被括号包裹，是方法名
                String methodName = ReUtil.findAll(Patterns.BRACKET, item, 0).get(0).trim();
                methodList.add(methodName.toUpperCase());
            } else {
                // 注释条目未被括号包裹，是url的一部分
                url.append(item).append(StrUtil.SPACE);
            }
        }

        if (methodList.isEmpty()) {
            Logger.info("[接口解析]终端:【{}】, 接口:【{}】, 缺失请求方式, 将同时支持GET和POST方式", terminal.getName(), api.getBreadcrumb());
            methodList.add("GET");
            methodList.add("POST");
        }

        if (url.length() == 0) {
            Logger.warn("[接口解析]终端:【{}】, 接口:【{}】, 缺失url", terminal.getName(), api.getBreadcrumb());
        }

        api.setMethodList(methodList);
        api.setUrl(url.toString().trim());
    }

}
