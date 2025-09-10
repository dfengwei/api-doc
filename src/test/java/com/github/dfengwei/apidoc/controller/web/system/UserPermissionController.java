package com.github.dfengwei.apidoc.controller.web.system;

/**
 * 管理端-用户权限Controller
 *
 * @author dfengwei@163.com
 */
public class UserPermissionController {

    /**
     * @apiName {1:系统} {1:用户} {1:权限} 1:列表
     * @apiAddr {GET} {POST} /api/web/system/user/list
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} [id] 用户id
     * @apiParam {Long} page 当前页码，从1开始
     * @apiParam {Long} pageSize 每页记录数
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONArray} data.list 符合查询条件的分页记录列表
     * @apiSuccess {char(21)} data.list.id 用户id
     * @apiSuccess {varchar(20)} data.list.permissionId 权限id
     * @apiSuccess {bigint} data.list.createTime 创建时间
     * @apiSuccess {bigint} data.list.modifyTime 修改时间
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void list() {

    }

    /**
     * @apiName {1:系统} {1:用户} {1:权限} 2:修改
     * @apiAddr {GET} {POST} /api/web/system/user/update
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 用户id
     * @apiParam {JSONArray} permissionIdList 权限id列表
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void update() {

    }

}
