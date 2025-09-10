package com.github.dfengwei.apidoc.domain;

import java.util.List;

import lombok.Data;

/**
 * 请求失败返回字段
 *
 * @author dfengwei@163.com
 */
@Data
public class ResponseFail {

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
     * 字段列表
     */
    private List<ResponseFail> children;

    /**
     * 注释内容
     */
    private String comment;

}
