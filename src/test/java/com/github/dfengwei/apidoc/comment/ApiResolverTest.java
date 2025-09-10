package com.github.dfengwei.apidoc.comment;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.resolver.comment.ApiResolver;
import com.github.dfengwei.apidoc.resolver.comment.ApiSuccessResolver;

/**
 * 接口请求参数解析Test
 *
 * @author dfengwei@163.com
 */
public class ApiResolverTest {

    /**
     * 测试正确的@apiSuccess注释
     */
    @Test(groups = {"apiResolver"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiSuccess内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiName {2:业务} {2:订单} 1:详情\n");
        sb.append(" * @Description 订单详情接口描述\n");
        sb.append(" * @apiAddr {GET} {POST} /api/app/business/order/get\n");
        sb.append(" *\n");
        sb.append(" * @apiHeader {String} token Token\n");
        sb.append(" * @apiHeader {int} xxx xxx请求头, xxx请求头,\n");
        sb.append(" *            xxx请求头");
        sb.append(" *\n");
        sb.append(" * @apiParam {char(21)} id 部门id\n");
        sb.append(" * @apiParam {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiParam {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiParam {JSONArray} children 子部门\n");
        sb.append(" * @apiParam {char(21)} children.id 子部门id\n");
        sb.append(" * @apiParam {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiParam {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiParam {String} [remark] 部门备注\n");
        sb.append(" *\n");
        sb.append(" * @apiSuccess {char(21)} id 部门id\n");
        sb.append(" * @apiSuccess {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiSuccess {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiSuccess {JSONArray} children 子部门\n");
        sb.append(" * @apiSuccess {char(21)} children.id 子部门id\n");
        sb.append(" * @apiSuccess {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiSuccess {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiSuccess {String} [remark] 部门备注\n");
        sb.append(" *\n");
        sb.append(" * @apiFail {char(21)} id 部门id\n");
        sb.append(" * @apiFail {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiFail {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiFail {JSONArray} children 子部门\n");
        sb.append(" * @apiFail {char(21)} children.id 子部门id\n");
        sb.append(" * @apiFail {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiFail {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiFail {String} [remark] 部门备注\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiSuccessResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = !errProxy.containsOutput("WARN");
        success &= !errProxy.containsOutput("ERROR");

        assert success;
    }

    /**
     * 测试正确的@apiSuccess注释
     */
    @Test(groups = {"apiResolver"})
    public void testNoAddr() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiSuccess内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\n");
        sb.append(" * @apiName {2:业务} {2:订单} 1:详情\n");
        sb.append(" * @apiDescription 订单详情接口描述\n");
        // sb.append(" * @apiAddr {GET} {POST} /api/app/business/order/get\n");
        sb.append(" *\n");
        sb.append(" * @apiHeader {String} token Token\n");
        sb.append(" * @apiHeader {int} xxx xxx请求头, xxx请求头,\n");
        sb.append(" *            xxx请求头");
        sb.append(" *\n");
        sb.append(" * @apiParam {char(21)} id 部门id\n");
        sb.append(" * @apiParam {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiParam {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiParam {JSONArray} children 子部门\n");
        sb.append(" * @apiParam {char(21)} children.id 子部门id\n");
        sb.append(" * @apiParam {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiParam {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiParam {String} [remark] 部门备注\n");
        sb.append(" *\n");
        sb.append(" * @apiSuccess {char(21)} id 部门id\n");
        sb.append(" * @apiSuccess {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiSuccess {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiSuccess {JSONArray} children 子部门\n");
        sb.append(" * @apiSuccess {char(21)} children.id 子部门id\n");
        sb.append(" * @apiSuccess {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiSuccess {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiSuccess {String} [remark] 部门备注\n");
        sb.append(" *\n");
        sb.append(" * @apiFail {char(21)} id 部门id\n");
        sb.append(" * @apiFail {varchar(20)} [departmentName] 部门名称，部门名称，\n");
        sb.append(" *           部门名称，部门名称\n");
        sb.append(" * @apiFail {varchar (20)} departmentType 部门 类型\n");
        sb.append(" * @apiFail {JSONArray} children 子部门\n");
        sb.append(" * @apiFail {char(21)} children.id 子部门id\n");
        sb.append(" * @apiFail {varchar(20)} children.departmentName 子部门名称，子部门名称，\n");
        sb.append(" *           子部门名称，子部门名称\n");
        sb.append(" * @apiFail {String} [children.remark] 子部门备注\n");
        sb.append(" * @apiFail {String} [remark] 部门备注\n");
        sb.append(" */\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &= !errProxy.containsOutput("ERROR");

        assert success;
    }

}