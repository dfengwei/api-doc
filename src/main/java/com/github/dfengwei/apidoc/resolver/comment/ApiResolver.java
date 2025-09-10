package com.github.dfengwei.apidoc.resolver.comment;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;

import cn.hutool.core.util.StrUtil;

/**
 * 接口解析器
 * 
 * @author dfengwei@163.com
 */

public class ApiResolver {

    /**
     * 解析
     * 
     * @param terminal 终端
     * @param api 接口
     */
    public static void resolve(Terminal terminal, Api api) {
        if (Logger.isDebugEnabled()) {
            Logger.debug("[接口解析]注释内容: 【{}】",
                StrUtil.removeAllLineBreaks(StrUtil.subPre(api.getComment(), 80)).trim() + "...");
        }

        // 解析接口名称（包含分组名称）
        ApiNameResolver.resolve(terminal, api);

        // 解析接口描述
        ApiDescriptionResolver.resolve(terminal, api);

        // 解析请求地址
        ApiAddrResolver.resolve(terminal, api);

        // 解析请求头
        ApiHeaderResolver.resolve(terminal, api);

        // 解析请求参数
        ApiParamResolver.resolve(terminal, api);

        // 解析成功返回
        ApiSuccessResolver.resolve(terminal, api);

        // 解析失败返回
        ApiFailResolver.resolve(terminal, api);
    }

}
