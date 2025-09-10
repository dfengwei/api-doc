package com.github.dfengwei.apidoc.generator.common;

import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;

/**
 * CommonMark（Markdown转HTML）
 * 
 * @author dfengwei@163.com
 */
public class CommonMark {

    /**
     * 扩展组件，包括表格组件等
     */
    private static final List<Extension> EXTENSIONS = ListUtil.of(TablesExtension.create());

    /**
     * 解析器
     */
    private static final Parser PARSER = Parser.builder().extensions(EXTENSIONS).build();

    /**
     * 渲染器
     */
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().extensions(EXTENSIONS).build();

    /**
     * 将Markdown渲染为HTML
     *
     * @param markdown markdown内容文本
     * @return HTML内容文本
     */
    public static String render(String markdown) {
        Node parsedMarkdown;

        try {
            parsedMarkdown = PARSER.parse(markdown);
        } catch (Exception e) {
            Logger.error("Markdown内容解析失败, 错误: {}", e.getMessage());
            return StrUtil.EMPTY;
        }

        try {
            return RENDERER.render(parsedMarkdown);
        } catch (Exception e) {
            Logger.error("Markdown内容渲染失败, 错误: {}", e.getMessage());
            return StrUtil.EMPTY;
        }
    }

}
