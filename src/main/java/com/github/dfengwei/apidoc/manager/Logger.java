package com.github.dfengwei.apidoc.manager;

import cn.hutool.core.util.StrUtil;

/**
 * 日志记录器
 *
 * @author dfengwei@163.com
 */
public class Logger {

    /**
     * 日志级别，包括：TRACE、DEBUG、INFO、WARN、ERROR，默认为：INFO
     */
    public static int loggerLevel = 3;

    /**
     * 是否开启了TRACE日志
     * 
     * @return 是否开启了TRACE日志
     */
    public static boolean isTraceEnabled() {
        return loggerLevel <= 1;
    }

    /**
     * 是否开启了DEBUG日志
     *
     * @return 是否开启了DEBUG日志
     */
    public static boolean isDebugEnabled() {
        return loggerLevel <= 2;
    }

    /**
     * 是否开启了INFO日志
     *
     * @return 是否开启了INFO日志
     */
    public static boolean isInfoEnabled() {
        return loggerLevel <= 3;
    }

    /**
     * 设置日志级别，包括：TRACE、DEBUG、INFO、WARN、ERROR，默认为：INFO
     * 
     * @param level 日志级别
     */
    public static void setLevel(String level) {
        switch (level.toUpperCase()) {
            case "TRACE":
                loggerLevel = 1;
                break;
            case "DEBUG":
                loggerLevel = 2;
                break;
            case "INFO":
                loggerLevel = 3;
                break;
            case "WARN":
                loggerLevel = 4;
                break;
            case "ERROR":
                loggerLevel = 5;
                break;
            default:
                error("错误的日志级别:【{}】, 正确的日志级别包括: TRACE、DEBUG、INFO、WARN、ERROR", level);
        }
    }

    /**
     * TRACE日志
     * 
     * @param message 带占位符的日志文本
     * @param args 参数
     */
    public static void trace(String message, Object... args) {
        if (loggerLevel <= 1) {
            System.out.println("[api-doc][TRACE]" + StrUtil.format(message, args));
        }
    }

    /**
     * DEBUG日志
     * 
     * @param message 带占位符的日志文本
     * @param args 参数
     */
    public static void debug(String message, Object... args) {
        if (loggerLevel <= 2) {
            System.out.println("[api-doc][DEBUG]" + StrUtil.format(message, args));
        }
    }

    /**
     * INFO日志
     * 
     * @param message 带占位符的日志文本
     * @param args 参数
     */
    public static void info(String message, Object... args) {
        if (loggerLevel <= 3) {
            System.out.println("[api-doc][INFO]" + StrUtil.format(message, args));
        }
    }

    /**
     * WARN日志
     * 
     * @param message 带占位符的日志文本
     * @param args 参数
     */
    public static void warn(String message, Object... args) {
        if (loggerLevel <= 4) {
            System.err.println("[api-doc][WARN]" + StrUtil.format(message, args));
        }
    }

    /**
     * ERROR日志
     * 
     * @param message 带占位符的日志文本
     * @param args 参数
     */
    public static void error(String message, Object... args) {
        if (loggerLevel <= 5) {
            System.err.println("[api-doc][ERROR]" + StrUtil.format(message, args));
        }
    }

}
