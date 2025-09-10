<#-- 请求头渲染宏 -->
<#macro renderRequestHeader requestHeader level key>
    <tr class="level-${level}" data-key="${key}">
        <td>
            <span class="field-name<#if requestHeader.required> required</#if>">${requestHeader.name}</span>
        </td>
        <td>${requestHeader.dataType}</td>
        <td>${requestHeader.description}</td>
    </tr>
    <#if requestHeader.children?has_content>
        <#list requestHeader.children>
            <ul class="content">
                <#items as child>
                    <@renderRequestHeader requestHeader=child level=level+1 key=key+'-'+child?index/>
                </#items>
            </ul>
        </#list>
    </#if>
</#macro>

<#-- 请求参数渲染宏 -->
<#macro renderRequestParam requestParam level key>
    <tr class="level-${level}" data-key="${key}">
        <td>
            <span class="field-name<#if requestParam.required> required</#if>">${requestParam.name}</span><#if requestParam.children?has_content><span class="toggle expended"></span></#if>
        </td>
        <td>${requestParam.dataType}</td>
        <td>${requestParam.description}</td>
    </tr>
    <#if requestParam.children?has_content>
        <#list requestParam.children>
            <ul class="content">
                <#items as child>
                    <@renderRequestParam requestParam=child level=level+1 key=key+'-'+child?index/>
                </#items>
            </ul>
        </#list>
    </#if>
</#macro>

<#-- 成功返回渲染宏 -->
<#macro renderResponseSuccess responseSuccess level key>
    <tr class="level-${level}" data-key="${key}">
        <td>
            <span class="field-name<#if responseSuccess.required> required</#if>">${responseSuccess.name}</span><#if responseSuccess.children?has_content><span class="toggle expended"></span></#if>
        </td>
        <td>${responseSuccess.dataType}</td>
        <td>${responseSuccess.description}</td>
    </tr>
    <#if responseSuccess.children?has_content>
        <#list responseSuccess.children>
            <ul class="content">
                <#items as child>
                    <@renderResponseSuccess responseSuccess=child level=level+1 key=key+'-'+child?index/>
                </#items>
            </ul>
        </#list>
    </#if>
</#macro>

<#-- 失败返回渲染宏 -->
<#macro renderResponseFail responseFail level key>
    <tr class="level-${level}" data-key="${key}">
        <td>
            <span class="field-name<#if responseFail.required> required</#if>">${responseFail.name}</span><#if responseFail.children?has_content><span class="toggle expended"></span></#if>
        </td>
        <td>${responseFail.dataType}</td>
        <td>${responseFail.description}</td>
    </tr>
    <#if responseFail.children?has_content>
        <#list responseFail.children>
            <ul class="content">
                <#items as child>
                    <@renderResponseFail responseFail=child level=level+1 key=key+'-'+child?index/>
                </#items>
            </ul>
        </#list>
    </#if>
</#macro>

<section class="api" id="${api.id}">
    <div class="api-name">
        <#if api.groupList?has_content>
            <span class="group"><#list api.groupList as g>${g.name} > </#list></span>
        </#if>
        <span>${api.name}</span>
    </div>
    <#if api.description?has_content>
        <div class="api-description">${api.description}</div>
    </#if>
    <div class="api-addr">
        <#list api.methodList>
            <#items as m>
                <span class="api-${m?lower_case}">${m}</span>
            </#items>
        </#list>
        <span class="api-url">${api.url}</span>
    </div>

    <#if api.requestHeaderList?has_content>
        <table class="api-content api-request-header">
            <caption>
                请求头:<#if !api.requestHeaderList?has_content>（无）</#if>
            </caption>
            <thead>
            <tr>
                <th>字段</th>
                <th>类型</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody>
            <#list api.requestHeaderList as h>
                <@renderRequestHeader requestHeader=h level=0 key=h?index/>
            </#list>
            </tbody>
        </table>
    </#if>

    <table class="api-content api-request-param">
        <caption>
            请求参数:<#if !api.requestParamList?has_content>（无）</#if>
        </caption>
        <#if api.requestParamList?has_content>
            <thead>
            <tr>
                <th>字段</th>
                <th>类型</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody>
            <#list api.requestParamList as p>
                <@renderRequestParam requestParam=p level=0 key=p?index/>
            </#list>
            </tbody>
        </#if>
    </table>

    <table class="api-content api-response-success">
        <caption>
            成功返回:<#if !api.responseSuccessList?has_content>（无）</#if>
        </caption>
        <#if api.responseSuccessList?has_content>
            <thead>
            <tr>
                <th>字段</th>
                <th>类型</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody>
            <#list api.responseSuccessList as s>
                <@renderResponseSuccess responseSuccess=s level=0 key=s?index/>
            </#list>
            </tbody>
        </#if>
    </table>

    <table class="api-content api-response-fail">
        <caption>
            失败返回:<#if !api.responseFailList?has_content>（无）</#if>
        </caption>
        <#if api.responseFailList?has_content>
            <thead>
            <tr>
                <th>字段</th>
                <th>类型</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody>
            <#list api.responseFailList as f>
                <@renderResponseFail responseFail=f level=0 key=f?index/>
            </#list>
            </tbody>
        </#if>
    </table>
</section>