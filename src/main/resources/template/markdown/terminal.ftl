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

<#--终端头部Markdown-->
<#list terminal.markdownReferenceList?filter(md -> md.type == 'HEADER')>
    <#items as md>
        ${markdownMap[md.id]}<#lt>
    </#items>
</#list>

## 接口<#lt>
<#--递归渲染组-->
<#list terminal.getGroupList()>
    <#items as group>
        <@buildGroup group=group/>
    </#items>
</#list>

<#--终端尾部Markdown-->
<#list terminal.markdownReferenceList?filter(md -> md.type == 'FOOTER')>
    <#items as md>
        ${markdownMap[md.id]}<#lt>
    </#items>
</#list>