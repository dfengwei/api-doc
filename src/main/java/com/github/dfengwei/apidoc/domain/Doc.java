package com.github.dfengwei.apidoc.domain;

import java.util.List;

import lombok.Data;

/**
 * 文档
 *
 * @author dfengwei@163.com
 */
@Data
public class Doc {

    /**
     * 文档名称（HTML文件的title、Markdown文件的文件名）
     */
    private String docName;

    /**
     * HTML生成器配置
     */
    private HtmlRendererConfig htmlRendererConfig;

    /**
     * Markdown生成器配置
     */
    private MarkdownRendererConfig markdownRendererConfig;

    /**
     * Markdown引用文件列表
     */
    private List<MarkdownReference> markdownReferenceList;

    /**
     * 终端列表
     */
    private List<Terminal> terminalList;

}
