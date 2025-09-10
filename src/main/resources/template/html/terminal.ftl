<#--组渲染宏，递归渲染组-->
<#macro buildGroup group>
<#--组内接口列表-->
    <#list group.getApiList()>
        <#items as api>
            <#include "api.ftl">
        </#items>
    </#list>
<#--子组列表-->
    <#list group.getChildGroupList()>
        <#items as childGroup>
            <@buildGroup group=childGroup />
        </#items>
    </#list>
</#macro>
<#--计算Markdown容器样式-->
<#macro calMdClass childQuantity>
    <#compress>
        <#if childQuantity == 1>
            mds-1
        <#elseif childQuantity == 2>
            mds-2
        <#elseif childQuantity >= 3>
            mds-2
        </#if>
    </#compress>
</#macro>
<div class="terminal hidden" data-terminal-id="${terminal.getId()}">
    <#--终端头部Markdown-->
    <#assign headerMdList = terminal.markdownReferenceList?filter(md -> md.type == 'HEADER')>
    <#list headerMdList>
        <div class="<@calMdClass childQuantity=headerMdList?size/>">
            <#items as md>
                <div id="${md.getId()}" class="md markdown-body">
                    ${markdownMap[md.getId()]}
                </div>
            </#items>
        </div>
    </#list>

    <#--递归渲染组-->
    <#list terminal.getGroupList()>
        <div class="apis">
            <#items as group>
                <@buildGroup group=group/>
            </#items>
        </div>
    </#list>

    <#--终端尾部Markdown-->
    <#assign footerMdList = terminal.markdownReferenceList?filter(md -> md.type == 'FOOTER')>
    <#list footerMdList>
        <div class="<@calMdClass childQuantity=footerMdList?size/>">
            <#items as md>
                <div id="${md.getId()}" class="md markdown-body">
                    ${markdownMap[md.getId()]}
                </div>
            </#items>
        </div>
    </#list>
</div>