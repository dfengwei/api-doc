package com.github.dfengwei.apidoc.domain;

import lombok.Data;

/**
 * Markdown渲染器配置
 *
 * @author dfengwei@163.com
 */
@Data
public class MarkdownRendererConfig {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 输出路径
     */
    private String path;

    /**
     * 输出绝对路径
     */
    private String absolutePath;

}
