package com.github.dfengwei.apidoc.domain;

import lombok.Data;

/**
 * HTML渲染器配置
 *
 * @author dfengwei@163.com
 */
@Data
public class HtmlRendererConfig {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 是否单文件模式
     * <ul>
     * <li>true：输出单个HTML文件</li>
     * <li>false：输出一个HTML文件，以及其引用的资源文件</li>
     * </ul>
     * 默认值：true
     */
    private Boolean singleFile;

    /**
     * 是否压缩，默认为
     * <ul>
     * <li>true：压缩HTML等文件</li>
     * <li>false：不压缩HTML等文件</li>
     * </ul>
     * 默认值：true
     */
    private Boolean compress;

    /**
     * 输出路径
     */
    private String path;

    /**
     * 输出绝对路径
     */
    private String absolutePath;

}
