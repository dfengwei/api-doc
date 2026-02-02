package com.github.dfengwei.apidoc.resolver.comment;

import java.util.regex.Pattern;

import cn.hutool.core.util.StrUtil;

/**
 * 正则表达式
 *
 * @author dfengwei@163.com
 */
public class Patterns {

    /**
     * 换行
     */
    public static Pattern LINE_FEED = Pattern.compile("\\s*\\n\\s*\\*\\s*");

    /**
     * 单条注释内容分割
     */
    public static Pattern SEPARATOR = Pattern.compile("\\[.+?\\]|\\{.+?\\}|\\S+");

    /**
     * 括号包裹（大括号或中括号）
     */
    public static Pattern BRACKET = Pattern.compile("(?<=\\{).+?(?=\\})|(?<=\\[).+?(?=\\])");

    /**
     * 中括号包裹
     */
    public static Pattern SQUARE_BRACKET = Pattern.compile("(?<=\\[).+?(?=\\])");

    /**
     * 大括号包裹
     */
    public static Pattern CURLY_BRACKET = Pattern.compile("(?<=\\{).+?(?=\\})");

    /**
     * @apiXxx内容
     */
    private static final String API_XXX = "(?<=@api{})((.|\\n)+?)(?=\\*\\s@api|$)";

    /**
     * apiDisable内容
     */
    public static Pattern API_DISABLE = Pattern.compile(StrUtil.format(API_XXX, "Disable"));

    /**
     * apiName内容
     */
    public static Pattern API_NAME = Pattern.compile(StrUtil.format(API_XXX, "Name"));

    /**
     * apiDescription内容
     */
    public static Pattern API_DESCRIPTION = Pattern.compile(StrUtil.format(API_XXX, "Description"));

    /**
     * apiAddr内容
     */
    public static Pattern API_ADDR = Pattern.compile(StrUtil.format(API_XXX, "Addr"));

    /**
     * apiHeader内容
     */
    public static Pattern API_HEADER = Pattern.compile(StrUtil.format(API_XXX, "Header"));

    /**
     * apiParam内容
     */
    public static Pattern API_PARAM = Pattern.compile(StrUtil.format(API_XXX, "Param"));

    /**
     * apiSuccess内容
     */
    public static Pattern API_SUCCESS = Pattern.compile(StrUtil.format(API_XXX, "Success"));

    /**
     * apiFail内容
     */
    public static Pattern API_FAIL = Pattern.compile(StrUtil.format(API_XXX, "Fail"));

}
