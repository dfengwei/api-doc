package com.github.dfengwei.apidoc.resolver.comment;

import java.util.List;

import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;

import cn.hutool.core.util.ReUtil;

/**
 * 接口是否禁用解析器
 *
 * @author dfengwei@163.com
 */
public class ApiDisableResolver {

    /**
     * 解析
     *
     * @param terminal 终端
     * @param api 接口
     */
    public static void resolve(Terminal terminal, Api api) {
        // 解析接口是否禁用，格式为：@apiDisable
        List<String> commentList = ReUtil.findAll(Patterns.API_DISABLE, api.getComment(), 1);
        api.setDisabled(commentList.size() > 0);
    }

}
