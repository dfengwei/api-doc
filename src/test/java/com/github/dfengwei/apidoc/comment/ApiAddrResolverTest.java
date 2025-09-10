package com.github.dfengwei.apidoc.comment;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.resolver.comment.ApiAddrResolver;

/**
 * 接口地址解析Test
 *
 * @author dfengwei@163.com
 */
public class ApiAddrResolverTest {

    /**
     * 测试正确的@apiDescription注释
     */
    @Test(groups = {"apiResolver", "apiAddrResolver"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiAddr {get} {Post}\n");
        sb.append(" *          http://xxx/yyy/zzz\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiAddrResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = !errProxy.containsOutput("WARN");
        success &= !errProxy.containsOutput("ERROR");

        assert success;
    }

    /**
     * 测试缺失@apiAddr注释
     */
    @Test(groups = {"apiResolver", "apiAddrResolver"})
    public void testMissingApiAddr() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append("");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiAddrResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("ERROR");
        success &= errProxy.containsOutput("缺失@apiAddr");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    /**
     * 测试重复的@apiDescription注释
     */
    @Test(groups = {"apiResolver", "apiAddrResolver"})
    public void testDuplicateApiName() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiAddr {get} {Post}\n");
        sb.append(" *          /xxx/yyy\n");
        sb.append(" * @apiAddr {get} {Post}\n");
        sb.append(" *          /xxx/zzz\n");
        sb.append(" * @apiAddr {1:业务} {2:产品} 2:修改");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiAddrResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &= errProxy.containsOutput("拥有多个@apiAddr");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    /**
     * 测试@apiDescription注释中没有组名或接口名
     */
    @Test(groups = {"apiResolver", "apiAddrResolver"})
    public void testMissingMethod() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiAddr \n");
        sb.append(" *          /xxx/yyy\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiAddrResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("INFO");
        success &= errProxy.containsOutput("缺失请求方式");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    /**
     * 测试@apiDescription注释中没有组名或接口名
     */
    @Test(groups = {"apiResolver", "apiAddrResolver"})
    public void testMissingUrl() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiAddr {post}\n");
        sb.append(" * @apiDescription xxx");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiAddrResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &= errProxy.containsOutput("缺失url");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}