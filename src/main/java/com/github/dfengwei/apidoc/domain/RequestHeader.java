package com.github.dfengwei.apidoc.domain;

import java.util.List;

import lombok.Data;

/**
 * 请求头
 *
 * @author dfengwei@163.com
 */
@Data
public class RequestHeader {

    /**
     * 全限定名称
     */
    private String fullName;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 描述
     */
    private String description;

    /**
     * 子元素列表
     */
    private List<RequestHeader> children;

    /**
     * 注释内容
     */
    private String comment;

}
