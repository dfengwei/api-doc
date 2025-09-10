package com.github.dfengwei.apidoc.generator.html;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.mock.ApiMocker;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;

/**
 * 接口TMl渲染测试
 *
 * @author dfengwei@163.com
 */
public class ApiHtmlRenderTest {

    @Test(groups = {"Generator"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟接口对象
        Api api = ApiMocker.mock();

        // 渲染接口HTML
        String html = ApiHtmlRenderer.render(api);

        // 是否生成成功
        boolean success = StrUtil.isNotBlank(html);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}
