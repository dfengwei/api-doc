package com.github.dfengwei.apidoc.comment;

import org.testng.annotations.Test;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.PrintStreamProxy;
import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.resolver.comment.ApiDescriptionResolver;

/**
 * 接口说明解析Test
 *
 * @author dfengwei@163.com
 */
public class ApiDescriptionResolverTest {

    /**
     * 测试正确的@apiDescription注释
     */
    @Test(groups = {"apiResolver", "apiDescriptionResolver"})
    public void testSuccess() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiDescription 用于根据商品 ID 或商品名称，查询单个或多个商品的基础信息（包括商品 ID、名称、价格、库存、分类等），\n");
        sb.append(" *          支持精确匹配与模糊匹配。\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiDescriptionResolver.resolve(terminal, api);

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        // 是否达到测试目的
        boolean success = !errProxy.containsOutput("WARN");
        success &= !errProxy.containsOutput("ERROR");

        assert success;
    }

    /**
     * 测试重复的@apiDescription注释
     */
    @Test(groups = {"apiResolver", "apiDescriptionResolver"})
    public void testDuplicateApiName() {
        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        // 日志级别设置为TRACE
        Logger.setLevel("TRACE");

        // 模拟@apiDescription内容
        StringBuilder sb = new StringBuilder();
        sb.append(" * @apiDescription 用于根据商品 ID 或商品名称，查询单个或多个商品的基础信息（包括商品 ID、名称、价格、库存、分类等），\n");
        sb.append(" *          支持精确匹配与模糊匹配。\n");
        sb.append(" * @apiDescription 用于根据商品 ID 或商品名称，查询单个或多个商品的基础信息（包括商品 ID、名称、价格、库存、分类等），\n");
        sb.append(" *          支持精确匹配与模糊匹配。\n");

        Terminal terminal = new Terminal();
        terminal.setName("APP");

        Api api = new Api();
        api.setName("yyy接口");
        api.setBreadcrumb("xxx分组→yyy接口");
        api.setComment(sb.toString());

        // 解析
        ApiDescriptionResolver.resolve(terminal, api);

        // 是否达到测试目的
        boolean success = errProxy.containsOutput("WARN");
        success &=  errProxy.containsOutput("拥有多个@apiDescription");

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}