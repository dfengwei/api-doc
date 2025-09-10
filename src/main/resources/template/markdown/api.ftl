<#-- 请求头渲染宏 -->
<#macro renderRequestHeader requestHeader level>
    | ${""?left_pad(level*6, "&emsp;")}${requestHeader.name} | ${requestHeader.dataType} | <#if requestHeader.required>是<#else>否</#if> | ${requestHeader.description} |<#lt>
    <#list requestHeader.children! as child>
        <@renderRequestHeader requestHeader=child level=level+1/>
    </#list>
</#macro>

<#-- 请求参数渲染宏 -->
<#macro renderRequestParam requestParam level>
    | ${""?left_pad(level*6, "&emsp;")}${requestParam.name} | ${requestParam.dataType} | <#if requestParam.required>是<#else>否</#if> | ${requestParam.description} |<#lt>
    <#list requestParam.children! as child>
        <@renderRequestParam requestParam=child level=level+1/>
    </#list>
</#macro>

<#-- 成功返回渲染宏 -->
<#macro renderResponseSuccess responseSuccess level>
    | ${""?left_pad(level*6, "&emsp;")}${responseSuccess.name} | ${responseSuccess.dataType} | <#if responseSuccess.required>是<#else>否</#if> | ${responseSuccess.description} |<#lt>
    <#list responseSuccess.children! as child>
        <@renderResponseSuccess responseSuccess=child level=level+1/>
    </#list>
</#macro>

<#-- 失败返回渲染宏 -->
<#macro renderResponseFail responseFail level>
    | ${""?left_pad(level*6, "&emsp;")}${responseFail.name} | ${responseFail.dataType} | <#if responseFail.required>是<#else>否</#if> | ${responseFail.description} |<#lt>
    <#list responseFail.children! as child>
        <@renderResponseFail responseFail=child level=level+1/>
    </#list>
</#macro>

<#list api.groupList>
    ### <#t>
    <#items as g>
        ${g.name} > <#t>
    </#items>
</#list>
${api.name}
<#if api.description?has_content>
    > ${api.description}<#lt>
</#if>
<#list api.methodList>
    <#items as m>
        `${m}` <#t>
    </#items>
</#list>
${api.url}

#### 请求头
| 字段 | 类型 | 必填 | 描述 |
| :- | :-: | :-: | :- |
<#list api.requestHeaderList! as h>
    <@renderRequestHeader requestHeader=h level=0/><#lt>
</#list>
#### 请求参数
| 字段 | 类型 | 必填 | 描述 |
| :- | :-: | :-: | :- |
<#list api.requestParamList! as p>
    <@renderRequestParam requestParam=p level=0/><#lt>
</#list>
#### 成功返回
| 字段 | 类型 | 必返回 | 描述 |
| :- | :-: | :-: | :- |
<#list api.responseSuccessList! as s>
    <@renderResponseSuccess responseSuccess=s level=0/><#lt>
</#list>
#### 失败返回
| 字段 | 类型 | 必返回 | 描述 |
| :- | :-: | :-: | :- |
<#list api.responseFailList! as f>
    <@renderResponseFail responseFail=f level=0/><#lt>
</#list>