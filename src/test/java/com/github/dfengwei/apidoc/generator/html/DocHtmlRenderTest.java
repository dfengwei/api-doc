package com.github.dfengwei.apidoc.generator.html;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.mock.DocMocker;

import cn.hutool.core.util.StrUtil;

/**
 * 文档HTML生成测试
 *
 * @author dfengwei@163.com
 */
public class DocHtmlRenderTest {

    @Test(groups = {"Generator"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟文档对象
        Doc doc = DocMocker.mock();

        // 渲染文档HTML
        String docHtml = DocHtmlRenderer.generate(doc);

        // 是否生成成功
        boolean success = StrUtil.isNotBlank(docHtml);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}
