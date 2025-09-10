package com.github.dfengwei.apidoc.controller.client.business;

/**
 * 客户端-产品Controller
 *
 * @author dfengwei@163.com
 */
public class ProductController {

    /**
     * @apiName {2:业务} {1:产品} 1:详情
     * @apiAddr {GET} {POST} /api/client/business/product/get
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 产品id
     *
     * @apiSuccess {String} code 返回码, 成功固定返回：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONObject} data.product 产品明细
     * @apiSuccess {char(21)} data.product.id 产品id
     * @apiSuccess {varchar(50)} data.product.productName 产品名称
     * @apiSuccess {varchar(2000)} data.product.productDescription 产品描述
     * @apiSuccess {JSONArray} data.product.categoryList 产品分类对象列表
     * @apiSuccess {String} data.product.categoryList.code 产品分类编号
     * @apiSuccess {String} data.product.categoryList.name 产品分类名称
     * @apiSuccess {bigint} data.product.createTime 创建时间
     * @apiSuccess {bigint} data.product.modifyTime 修改时间
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void get() {

    }

    /**
     * @apiName {2:业务} {1:产品} 1:列表
     * @apiAddr {GET} {POST} /api/client/business/product/list
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} [id] 产品id
     * @apiParam {String} [productName] 产品名称
     * @apiParam {String} [productNameLike] 产品名称（模糊匹配）
     * @apiParam {Long} page 当前页码，从1开始
     * @apiParam {Long} pageSize 每页记录数
     *
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {JSONArray} data.list 符合查询条件的分页记录列表
     * @apiSuccess {char(21)} data.list.id 产品id
     * @apiSuccess {varchar(50)} data.list.productName 产品名称
     * @apiSuccess {varchar(2000)} data.list.productDescription 产品描述
     * @apiSuccess {JSONArray} data.list.categoryList 产品分类对象列表
     * @apiSuccess {String} data.list.categoryList.code 产品分类编号
     * @apiSuccess {String} data.list.categoryList.name 产品分类名称
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
     * @apiName {2:业务} {1:产品} 3:新增
     * @apiAddr {GET} {POST} /api/client/business/product/create
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {varchar(50)} productName 产品名称
     * @apiParam {varchar(2000)} [productDescription] 产品描述
     * @apiParam {JSONArray} [categoryList] 产品分类对象列表
     * @apiParam {String} categoryList.code 产品分类对象编号
     * @apiParam {String} categoryList.name 产品分类对象名称
     * 
     * @apiSuccess {String} code 返回码, 固定为：00000
     * @apiSuccess {JSONObject} data 返回的数据
     * @apiSuccess {char(21)} data.id 新增产品的id
     *
     * @apiFail {String} code 返回码
     * @apiFail {String} [msg] 提示信息，可展现给终端用户
     * @apiFail {String} [error] 错误信息，不建议展现给终端用户
     */
    public void create() {

    }

    /**
     * @apiName {2:业务} {1:产品} 4:修改
     * @apiAddr {GET} {POST} /api/client/business/product/update
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {String} id 产品id
     * @apiParam {varchar(50)} [productName] 产品名称
     * @apiParam {varchar(2000)} [productDescription] 产品描述
     * @apiParam {JSONArray} [categoryList] 产品分类对象列表
     * @apiParam {String} categoryList.code 产品分类对象编号
     * @apiParam {String} categoryList.name 产品分类对象名称
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
     * @apiName {2:业务} {1:产品} 5:删除
     * @apiAddr {GET} {POST} /api/client/business/product/delete
     *
     * @apiHeader {String} token Token
     *
     * @apiParam {JSONArray} idList 产品id列表，格式：["xxx","yyy"]
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
