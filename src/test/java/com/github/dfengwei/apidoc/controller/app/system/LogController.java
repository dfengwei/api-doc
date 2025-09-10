package com.github.dfengwei.apidoc.controller.app.system;

/**
 * APP端-日志Controller
 *
 * @author dfengwei@163.com
 */
public class LogController {

    /**
     * @apiName {1:系统} {2:日志} 1:详情
     * @apiAddr {GET} {POST} /api/app/system/log/get
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 日志id
     *
     * @apiSuccess {String} code 返回码, 成功固定返回：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONObject} data.log 日志明细
     * @apiSuccess {char(21)} data.log.id 日志id
     * @apiSuccess {varchar(200)} data.log.title 日志标题
     * @apiSuccess {longtext} [data.log.content] 日志内容
     * @apiSuccess {bigint} data.log.createTime 创建时间
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void get() {

    }

    /**
     * @apiName {1:系统} {2:日志} 2:列表
     * @apiAddr {GET} {POST} /api/app/system/log/list
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} [id] 日志id
     * @apiParam {String} [title] 日志标题（精确匹配）
     * @apiParam {String} [titleLike] 日志标题（模糊匹配）
     * @apiParam {String} [contentLike] 日志内容（模糊匹配）
     * @apiParam {Long} page 当前页码，从1开始
     * @apiParam {Long} pageSize 每页记录数
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONArray} data.list 符合查询条件的分页记录列表
     * @apiSuccess {char(21)} data.list.id 日志id
     * @apiSuccess {varchar(20)} data.list.logName 日志名称
     * @apiSuccess {varchar(200)} [data.list.remark] 日志备注
     * @apiSuccess {bigint} data.list.createTime 创建时间
     * @apiSuccess {bigint} data.list.modifyTime 修改时间
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void list() {

    }

}
