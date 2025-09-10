package com.github.dfengwei.apidoc.domain;

import java.util.List;

import lombok.Data;

/**
 * 分组
 *
 * @author dfengwei@163.com
 */
@Data
public class Group {

    /**
     * 名称
     */
    private String name;

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 子分组map
     */
    private List<Group> childGroupList;

    /**
     * 接口列表
     */
    private List<Api> apiList;

}
