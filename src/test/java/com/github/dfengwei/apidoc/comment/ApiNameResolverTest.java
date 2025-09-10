package com.github.dfengwei.apidoc.comment;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.resolver.comment.ApiNameResolver;

/**
 * 接口名称解析Test
 *
 * @author dfengwei@163.com
 */
public class ApiNameResolverTest {

    /**
     * 测试正确的@apiName注释
     */
    @Test(groups = {"apiResolver", "apiNameResolver"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiName内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiName {1:业务} {2:产品}\n");
        sb.append(" *          2:创建 接口\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiNameResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = !errProxy.containsOutput("WARN");
        success &= !errProxy.containsOutput("ERROR");

        assert success;
    }

    /**
     * 测试缺失@apiName注释
     */
    @Test(groups = {"apiResolver", "apiNameResolver"})
    public void testMissingApiName() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiName内容
        StringBuilder sb = new StringBuilder();
        sb.append("");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiNameResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("ERROR");
        success &=  errProxy.containsOutput("存在缺失@apiName的接口");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    /**
     * 测试重复的@apiName注释
     */
    @Test(groups = {"apiResolver", "apiNameResolver"})
    public void testDuplicateApiName() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiName内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiName {1:业务} {2:产品}\n");
        sb.append(" *          2:创建 接口\n");
        sb.append(" * @apiName {1:业务} {2:产品} 2:修改");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiNameResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &=  errProxy.containsOutput("存在拥有多个@apiName的接口");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    /**
     * 测试@apiName注释中没有组名或接口名
     */
    @Test(groups = {"apiResolver", "apiNameResolver"})
    public void testMissingGroupNameOrApiName() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiName内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiName 2:创建接口\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiNameResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("ERROR");
        success &=  errProxy.containsOutput("至少需要包含一个组名和一个接口名称");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    /**
     * 测试正确的@apiName注释
     */
    @Test(groups = {"apiResolver", "apiNameResolver"})
    public void testMissingGroupSequence() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiName内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiName {业务} {2:产品}\n");
        sb.append(" *          2:创建 接口\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiNameResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

         // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &=  errProxy.containsOutput("未包含序号");

        assert success;
    }

    /**
     * 测试正确的@apiName注释
     */
    @Test(groups = {"apiResolver", "apiNameResolver"})
    public void testMissingApiSequence() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiName内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiName {1:业务} {2:产品}\n");
        sb.append(" *          创建 接口\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiNameResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

         // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &=  errProxy.containsOutput("接口名称未包含序号");

        assert success;
    }

}