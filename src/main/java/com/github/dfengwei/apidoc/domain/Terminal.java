package com.github.dfengwei.apidoc.domain;

import java.nio.file.Path;
import java.util.List;

import lombok.Data;

/**
 * 终端
 *
 * @author dfengwei@163.com
 */
@Data
public class Terminal {

    /**
     * 终端ID
     */
    private String id;

    /**
     * 终端名称，例如：后台、客户端、WEB端、APP端等
     */
    private String name;

    /**
     * 扫描路径（源码文件所在路径）
     */
    private Path scanPath;

    /**
     * Markdown引用文件列表
     */
    private List<MarkdownReference> markdownReferenceList;

    /**
     * 分组列表
     */
    private List<Group> groupList;

}
