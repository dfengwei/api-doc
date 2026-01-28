package com.github.dfengwei.apidoc.controller.app.business;

/**
 * APP端-订单Controller
 *
 * @author dfengwei@163.com
 */
public class OrderController1 {

    /**
     * @apiName {2:业务} {2:订单} 2:详情
     * @apiAddr {GET} {POST} /api/app/business/order/get
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 订单id
     *
     * @apiSuccess {String} code 返回码, 成功固定返回：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONObject} data.order 订单明细
     * @apiSuccess {char(21)} data.order.id 订单id
     * @apiSuccess {varchar(200)} data.order.orderName 订单名称
     * @apiSuccess {JSONArray} data.order.productList 产品对象列表
     * @apiSuccess {String} data.order.productList.productId 产品id
     * @apiSuccess {int} data.order.productList.quantity 产品数量
     * @apiSuccess {bigint} data.order.createTime 创建时间
     * @apiSuccess {bigint} data.order.modifyTime 修改时间
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void get() {

    }

    /**
     * @apiName {2:业务} {2:订单} 3:新增
     * @apiAddr {GET} {POST} /api/app/business/order/create
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {varchar(50)} orderName 订单名称
     * @apiParam {JSONArray} [productList] 产品对象列表
     * @apiParam {char(21)} productList.productId 产品id
     * @apiParam {int} productList.quantity 产品数量
     * 
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {char(21)} data.id 新增订单的id
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void create() {

    }

    /**
     * @apiName {2:业务} {2:订单} 4:修改
     * @apiAddr {GET} {POST} /api/app/business/order/update
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 订单id
     * @apiParam {varchar(50)} [orderName] 订单名称
     * @apiParam {JSONArray} [productList] 产品对象列表
     * @apiParam {char(21)} [productList.productId] 产品id
     * @apiParam {int} [productList.quantity] 产品数量
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
     * @apiName {2:业务} {2:订单} 5:删除
     * @apiAddr {GET} {POST} /api/app/business/order/delete
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {JSONArray} idList 订单id列表，格式：["xxx","yyy"]
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
