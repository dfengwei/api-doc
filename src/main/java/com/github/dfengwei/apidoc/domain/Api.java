package com.github.dfengwei.apidoc.domain;

import java.util.List;

import lombok.Data;

/**
 * 接口
 *
 * @author dfengwei@163.com
 */
@Data
public class Api {

    /**
     * 接口ID
     */
    private String id;

    /**
     * 源码文件路径
     */
    private String sourceFilePath;

    /**
     * 注释内容
     */
    private String comment;

    /**
     * 分组列表
     */
    private List<Group> groupList;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 面包屑
     */
    private String breadcrumb;

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口调用方式列表，例如：GET、POST等
     */
    private List<String> methodList;

    /**
     * 接口URL
     */
    private String url;

    /**
     * 请求头列表
     */
    private List<RequestHeader> requestHeaderList;

    /**
     * 请求参数列表
     */
    private List<RequestParam> requestParamList;

    /**
     * 成功响应字段列表
     */
    private List<ResponseSuccess> responseSuccessList;

    /**
     * 失败响应字段列表
     */
    private List<ResponseFail> responseFailList;

}
