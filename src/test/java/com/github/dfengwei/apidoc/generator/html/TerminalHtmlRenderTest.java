package com.github.dfengwei.apidoc.generator.html;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.mock.TerminalMocker;

import cn.hutool.core.util.StrUtil;

/**
 * 终端HTML生成测试
 *
 * @author dfengwei@163.com
 */
public class TerminalHtmlRenderTest {

    @Test(groups = {"Generator"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟终端对象
        Terminal terminal = TerminalMocker.mock();

        // 渲染终端HTML
        String html = TerminalHtmlRenderer.generate(terminal);

        // 是否生成成功
        boolean success = StrUtil.isNotBlank(html);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}
