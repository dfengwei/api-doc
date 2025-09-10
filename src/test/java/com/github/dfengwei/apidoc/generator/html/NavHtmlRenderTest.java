package com.github.dfengwei.apidoc.generator.html;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.mock.DocMocker;

import cn.hutool.core.util.StrUtil;

/**
 * 导航HTML生成测试
 *
 * @author dfengwei@163.com
 */
public class NavHtmlRenderTest {

    @Test(groups = {"Generator"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟文档对象
        Doc doc = DocMocker.mock();

        // 渲染终端HTML
        String html = NavHtmlRenderer.generate(doc);

        // 是否生成成功
        boolean success = StrUtil.isNotBlank(html);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}
