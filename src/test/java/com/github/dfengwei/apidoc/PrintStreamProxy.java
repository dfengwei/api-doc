package com.github.dfengwei.apidoc;

import java.io.PrintStream;
import java.util.LinkedList;

import lombok.Getter;

/**
 * 打印流代理
 */
public class PrintStreamProxy extends PrintStream {

    @Getter
    private PrintStream originalPrintStream;

    private LinkedList<String> lineList = new LinkedList<>();

    public PrintStreamProxy(PrintStream printStream) {
        super(printStream);
        this.originalPrintStream = printStream;
    }

    @Override
    public void print(String s) {
        super.print(s);

        String msg = "";
        if (!lineList.isEmpty()) {
            msg = lineList.removeLast();
        }
        lineList.push(msg);
    }

    @Override
    public void println(String s) {
        super.println(s);
        lineList.push(s);
    }

    /**
     * 是否存在某个输出（模糊匹配）
     *
     * @param output 输出内容
     * @return 是否存在
     */
    public boolean containsOutput(String output) {
        for (String msg : lineList) {
            if (msg.contains(output)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否存在输出
     *
     * @return 是否存在输出
     */
    public boolean hasOutput() {
        return !lineList.isEmpty();
    }

}
