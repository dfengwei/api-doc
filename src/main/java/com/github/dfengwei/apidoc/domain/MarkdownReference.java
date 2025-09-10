package com.github.dfengwei.apidoc.domain;

import lombok.Data;

/**
 * Markdown文件引用
 *
 * @author dfengwei@163.com
 */
@Data
public class MarkdownReference {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型，HEADER：头部；FOOTER：底部
     */
    private String type;

    /**
     * 文件路径
     */
    private String filePath;

}
