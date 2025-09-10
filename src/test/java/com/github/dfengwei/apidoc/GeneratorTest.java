package com.github.dfengwei.apidoc;

import org.testng.annotations.Test;

public class GeneratorTest {

    @Test(groups = {"generator"})
    public void testSuccess() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        String configFilePath = "src/test/resources/api-doc.json";
        String logLevel = "INFO";

        Generator.main(new String[] {configFilePath, logLevel});

        boolean success = !errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    @Test(groups = {"generator"})
    public void testCompress() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        String configFilePath = "src/test/resources/api-doc-compress.json";
        String logLevel = "INFO";

        Generator.main(new String[] {configFilePath, logLevel});

        boolean success = !errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    @Test(groups = {"generator"})
    public void testSingleFile() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        String configFilePath = "src/test/resources/api-doc-singleFile.json";
        String logLevel = "INFO";

        Generator.main(new String[] {configFilePath, logLevel});

        boolean success = !errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }



    @Test(groups = {"generator"})
    public void testCompressedSingleFile() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        String configFilePath = "src/test/resources/api-doc-compressedSingleFile.json";
        String logLevel = "INFO";

        Generator.main(new String[] {configFilePath, logLevel});

        boolean success = !errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    @Test(groups = {"generator"})
    public void testEmptyArgs() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        Generator.main(new String[] {});

        boolean success = errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    @Test(groups = {"generator"})
    public void testMinConfig() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        String configFilePath = "src/test/resources/api-doc.min.json";
        String logLevel = "INFO";
        Generator.main(new String[] {configFilePath, logLevel});

        boolean success = !errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

    @Test(groups = {"generator"})
    public void testEmptyConfig() {
        System.out.println("执行测试: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        // 设置错误输出流代理
        PrintStreamProxy errProxy = new PrintStreamProxy(System.err);
        System.setErr(errProxy);

        String configFilePath = "src/test/resources/api-doc-empty.json";
        String logLevel = "INFO";

        Generator.main(new String[] {configFilePath, logLevel});

        boolean success = errProxy.hasOutput();

        // 还原输出流
        System.setErr(errProxy.getOriginalPrintStream());

        assert success;
    }

}
