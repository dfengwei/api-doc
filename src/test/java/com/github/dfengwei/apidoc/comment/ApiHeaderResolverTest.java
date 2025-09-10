package com.github.dfengwei.apidoc.comment;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.resolver.comment.ApiHeaderResolver;

/**
 * 接口请求头解析Test
 *
 * @author dfengwei@163.com
 */
public class ApiHeaderResolverTest {

    /**
     * 正确
     */
    @Test(groups = {"apiResolver", "apiHeaderResolver"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiHeader内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiHeader {String} token Token\n");
        sb.append(" * @apiHeader {int} xxx xxx请求头, xxx请求头,\n");
        sb.append(" *            xxx请求头");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiHeaderResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = !errProxy.containsOutput("WARN");
        success &= !errProxy.containsOutput("ERROR");

        assert success;
    }

    /**
     * 格式错误
     */
    @Test(groups = {"apiResolver", "apiHeaderResolver"})
    public void testWrongFormat() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiHeader内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiHeader {String} token\n");
        sb.append(" * @apiHeader {int} xxx xxx请求头, xxx请求头,\n");
        sb.append(" *            xxx请求头");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setSourceFilePath("/xxx/yyy.java");
        api.setComment(sb.toString());

        // 解析
        ApiHeaderResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("ERROR");
        success &= errProxy.containsOutput("请求头格式错误");

        assert success;
    }

    /**
     * 数据类型未使用大括号包裹
     */
    @Test(groups = {"apiResolver", "apiHeaderResolver"})
    public void testDataTypeWithoutBracket() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiHeader内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiHeader String token Token\n");
        sb.append(" * @apiHeader {int} xxx xxx请求头, xxx请求头,\n");
        sb.append(" *            xxx请求头");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiHeaderResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &= errProxy.containsOutput("数据类型建议使用大括号包裹");

        assert success;
    }

    /**
     * 测试正确的@apiHeader注释
     */
    @Test(groups = {"apiResolver", "apiHeaderResolver"})
    public void testDuplicateApiParam() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiHeader内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiHeader {char(21)} id 部门id-1\n");
        sb.append(" * @apiHeader {char(21)} id 部门id-2\n");
        sb.append(" * @apiHeader {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiHeader {varchar (20)} departmentType 部门 类型 1\n");
        sb.append(" * @apiHeader {varchar (20)} departmentType 部门 类型 2\n");
        sb.append(" * @apiHeader {JSONArray} children 子部门\n");
        sb.append(" * @apiHeader {char(21)} children.id 子部门id\n");
        sb.append(" * @apiHeader {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiHeader {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiHeader {String} [remark] 部门备注\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiHeaderResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &= errProxy.containsOutput("参数名称重复");

        assert success;
    }

    /**
     * 测试缺失@apiHeader注释
     */
    @Test(groups = {"apiResolver", "apiHeaderResolver"})
    public void testMissingParent() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiHeader内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiHeader {char(21)} id 部门id\n");
        sb.append(" * @apiHeader {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiHeader {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiHeader {JSONArray} children 子部门\n");
        sb.append(" * @apiHeader {char(21)} children.id 子部门id\n");
        sb.append(" * @apiHeader {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiHeader {String} [children1.remark] 子部门备注\n");
        sb.append(" * @apiHeader {String} [remark] 部门备注\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiHeaderResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("ERROR");
        success &= errProxy.containsOutput("无法找到父参数");

        assert success;
    }

    /**
     * 测试正确的@apiHeader注释
     */
    @Test(groups = {"apiResolver", "apiHeaderResolver"})
    public void testWrongDataTypeOfParamWithChildren() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiHeader内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiHeader {char(21)} id 部门id\n");
        sb.append(" * @apiHeader {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiHeader {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiHeader {String} children 子部门\n");
        sb.append(" * @apiHeader {char(21)} children.id 子部门id\n");
        sb.append(" * @apiHeader {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiHeader {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiHeader {String} [remark] 部门备注\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiHeaderResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &= errProxy.containsOutput("JSONObject或SONArray");

        assert success;
    }

}