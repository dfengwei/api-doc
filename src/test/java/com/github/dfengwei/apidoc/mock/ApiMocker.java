package com.github.dfengwei.apidoc.mock;

import java.util.ArrayList;
import java.util.List;

import com.github.dfengwei.apidoc.domain.*;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dfengwei.apidoc.manager.IdUtil;

/**
 * 接口模拟器
 */
public class ApiMocker {

    /**
     * 模拟接口信息对象
     * 
     * @return 接口信息对象
     */
    public static Api mock() {
        String apiName = StrUtil.format("接口-{}", RandomUtil.randomString(4));

        // 生成接口信息对象
        Api api = new Api();
        api.setId(IdUtil.get());
        api.setSourceFilePath("xxx/yyy/zzz");
        api.setComment("注释内容");

        List<Group> groupList = new ArrayList<>();
        Group group = new Group();
        group.setName("分组1");
        group.setSequence(1);
        groupList.add(group);

        group = new Group();
        group.setName("分组1-2");
        group.setSequence(1);
        groupList.add(group);

        group = new Group();
        group.setName("分组1-2-1");
        group.setSequence(1);
        groupList.add(group);

        api.setGroupList(groupList);
        api.setName(apiName);
        api.setDescription(apiName + "的说明");
        api.setMethodList(ListUtil.toList("GET", "POST"));
        api.setUrl("/api/xxx");

        // 生成请求头
        List<RequestHeader> apiHeaderList = new ArrayList<>();
        for (int i = 0; i < RandomUtil.randomInt(5); i++) {
            RequestHeader apiHeader = new RequestHeader();

            apiHeader.setName(RandomUtil.randomString(RandomUtil.randomInt(20)));
            apiHeader.setRequired(i % 2 == 1);
            apiHeader.setDataType("String");
            apiHeader.setDescription(apiHeader.getName() + "字段的说明");
            apiHeaderList.add(apiHeader);
        }
        api.setRequestHeaderList(apiHeaderList);

        // 生成请求参数
        List<RequestParam> requestParamList = new ArrayList<>();
        for (int i = 0; i < RandomUtil.randomInt(5); i++) {
            requestParamList.add(generateRequestParam());
        }
        api.setRequestParamList(requestParamList);

        // 生成成功返回结果
        List<ResponseSuccess> responseSuccessList = new ArrayList<>();
        for (int i = 0; i < RandomUtil.randomInt(5); i++) {
            responseSuccessList.add(generateResponseSuccess());
        }
        api.setResponseSuccessList(responseSuccessList);

        // 生成成功返回结果
        List<ResponseFail> responseFailList = new ArrayList<>();
        for (int i = 0; i < RandomUtil.randomInt(3); i++) {
            responseFailList.add(generateResponseFail());
        }
        api.setResponseFailList(responseFailList);

        return api;
    }

    private static RequestParam generateRequestParam() {
        RequestParam requestParam = new RequestParam();
        requestParam.setName(RandomUtil.randomString(RandomUtil.randomInt(5, 10)));
        requestParam.setRequired(RandomUtil.randomBoolean());
        requestParam.setDescription(requestParam.getName() + "字段的说明");
        requestParam.setChildren(new ArrayList<>());

        if (StrUtil.count(requestParam.getFullName(), StrUtil.DOT) <= 3) {
            for (int i = 0; i < RandomUtil.randomInt(3); i++) {
                requestParam.getChildren().add(generateRequestParam());
            }
        }

        if (CollUtil.isNotEmpty(requestParam.getChildren())) {
            requestParam.setDataType(RandomUtil.randomEle(ListUtil.of("JSONObject", "JSONArray")));
        } else {
            requestParam.setDataType(RandomUtil.randomEle(ListUtil.of("String", "Long", "Integer", "Double", "Boolean",
                "CHAR(21)", "VARCHAR(2000)", "DECIMAL(7,2)")));
        }

        return requestParam;
    }

    private static ResponseSuccess generateResponseSuccess() {
        ResponseSuccess responseSuccess = new ResponseSuccess();
        responseSuccess.setName(RandomUtil.randomString(RandomUtil.randomInt(5, 10)));
        responseSuccess.setRequired(RandomUtil.randomBoolean());
        responseSuccess.setDescription(responseSuccess.getName() + "字段的说明");
        responseSuccess.setChildren(new ArrayList<>());

        if (StrUtil.count(responseSuccess.getFullName(), StrUtil.DOT) <= 3) {
            for (int i = 0; i < RandomUtil.randomInt(3); i++) {
                responseSuccess.getChildren().add(generateResponseSuccess());
            }
        }

        if (CollUtil.isNotEmpty(responseSuccess.getChildren())) {
            responseSuccess.setDataType(RandomUtil.randomEle(ListUtil.of("JSONObject", "JSONArray")));
        } else {
            responseSuccess.setDataType(RandomUtil.randomEle(ListUtil.of("String", "Long", "Integer", "Double",
                "Boolean", "CHAR(21)", "VARCHAR(2000)", "DECIMAL(7,2)")));
        }

        return responseSuccess;
    }

    private static ResponseFail generateResponseFail() {
        ResponseFail responseFail = new ResponseFail();

        responseFail.setName(RandomUtil.randomString(RandomUtil.randomInt(5, 10)));
        responseFail.setRequired(RandomUtil.randomBoolean());
        responseFail.setDescription(responseFail.getName() + "字段的说明");
        responseFail.setChildren(new ArrayList<>());

        if (StrUtil.count(responseFail.getFullName(), StrUtil.DOT) <= 3) {
            for (int i = 0; i < RandomUtil.randomInt(3); i++) {
                responseFail.getChildren().add(generateResponseFail());
            }
        }

        if (CollUtil.isNotEmpty(responseFail.getChildren())) {
            responseFail.setDataType(RandomUtil.randomEle(ListUtil.of("JSONObject", "JSONArray")));
        } else {
            responseFail.setDataType(RandomUtil.randomEle(ListUtil.of("String", "Long", "Integer", "Double", "Boolean",
                "CHAR(21)", "VARCHAR(2000)", "DECIMAL(7,2)")));
        }

        return responseFail;
    }

}
