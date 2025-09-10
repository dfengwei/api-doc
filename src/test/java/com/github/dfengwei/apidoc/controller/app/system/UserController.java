package com.github.dfengwei.apidoc.controller.app.system;

/**
 * APP端-用户Controller
 *
 * @author dfengwei@163.com
 */
public class UserController {

    /**
     * @apiName {1:系统} {1:用户} 1:详情
     * @apiAddr {GET} {POST} /api/app/system/user/get
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 用户id
     *
     * @apiSuccess {String} code 返回码, 成功固定返回：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONObject} data.user 用户明细
     * @apiSuccess {char(21)} data.user.id 用户id
     * @apiSuccess {varchar(20)} data.user.userName 用户名称
     * @apiSuccess {varchar(200)} [data.user.remark] 用户备注
     * @apiSuccess {bigint} data.user.createTime 创建时间
     * @apiSuccess {bigint} data.user.modifyTime 修改时间
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void get() {

    }

    /**
     * @apiName {1:系统} {1:用户} 2:列表
     * @apiAddr {GET} {POST} /api/app/system/user/list
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} [id] 用户id
     * @apiParam {String} [userName] 用户名称（精确匹配）
     * @apiParam {String} [userNameLike] 用户名称（模糊匹配）
     * @apiParam {Long} page 当前页码，从1开始
     * @apiParam {Long} pageSize 每页记录数
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONArray} data.list 符合查询条件的分页记录列表
     * @apiSuccess {char(21)} data.list.id 用户id
     * @apiSuccess {varchar(20)} data.list.userName 用户名称
     * @apiSuccess {varchar(200)} [data.list.remark] 用户备注
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
     * @apiName {1:系统} {1:用户} 3:新增
     * @apiAddr {GET} {POST} /api/app/system/user/create
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {varchar(20)} userName 用户名称
     * @apiParam {varchar(200)} [remark] 用户备注
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {char(21)} data.id 新增用户的id
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void create() {

    }

    /**
     * @apiName {1:系统} {1:用户} 4:修改
     * @apiAddr {GET} {POST} /api/app/system/user/update
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 用户id
     * @apiParam {varchar(20)} [userName] 用户名称
     * @apiParam {varchar(200)} [remark] 用户备注
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void update() {

    }

    /**
     * @apiName {1:系统} {1:用户} 5:删除
     * @apiAddr {GET} {POST} /api/app/system/user/delete
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {JSONArray} idList 用户id列表，格式：["xxx","yyy"]
     *
     * @apiSuccess {String} code 返回码, 成功固定返回：00000
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void delete() {

    }

}
