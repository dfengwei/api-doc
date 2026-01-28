package com.github.dfengwei.apidoc.controller.app.business;

/**
 * APP端-订单Controller
 *
 * @author dfengwei@163.com
 */
public class OrderController2 {

    /**
     * @apiName {2:业务} {2:订单} 1:列表
     * @apiAddr {GET} {POST} /api/app/business/order/list
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} [id] 订单id
     * @apiParam {String} [orderName] 订单名称（精确匹配）
     * @apiParam {String} [orderNameLike] 订单名称（模糊匹配）
     * @apiParam {Long} page 当前页码，从1开始
     * @apiParam {Long} pageSize 每页记录数
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONArray} data.list 符合查询条件的分页记录列表
     * @apiSuccess {char(21)} data.list.id 订单id
     * @apiSuccess {varchar(200)} data.list.orderName 订单名称
     * @apiSuccess {JSONArray} data.list.productList 产品对象列表
     * @apiSuccess {char(21)} data.list.productList.productId 产品id
     * @apiSuccess {int} data.list.productList.quantity 产品数量
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
