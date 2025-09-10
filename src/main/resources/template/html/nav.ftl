<#--组渲染宏，递归渲染组-->
<#macro buildGroup group>
    <li class="group">
        <#--组标题-->
        <div class="title">
            <#--组标题文字-->
            <span class="text">${group.name}</span>
            <#--组标题折叠按钮-->
            <span class="arrow expended"></span>
        </div>
        <#--组内接口列表-->
        <#list group.apiList>
            <div class="apis">
                <#items as api>
                    <a class="api" href="#${api.id}">${api.name}</a>
                </#items>
            </div>
        </#list>
        <#--子组列表-->
        <#list group.childGroupList>
            <ul class="content">
                <#items as childGroup>
                    <@buildGroup group=childGroup />
                </#items>
            </ul>
        </#list>
    </li>
</#macro>

<#--终端导航，用于选择哪个终端的文档-->
<ul id="terminal-nav">
    <#list doc.terminalList as terminal>
        <label>
            <input type="checkbox" data-terminal-id="${terminal.id}" data-terminal-name="${terminal.name}">
            <span>${terminal.name}</span>
        </label>
    </#list>
</ul>
<#--主导航-->
<ul id="main-nav">
    <#--文档头部Markdown-->
    <#list doc.markdownReferenceList?filter(md -> md.type == 'HEADER') as md>
        <li class="md doc"><a href="#${md.id}">${md.name}</a></li>
    </#list>
    <#--各个终端内容-->
    <#list doc.terminalList as terminal>
        <ul class="terminal hidden" data-terminal-id="${terminal.id}">
            <#--终端头部Markdown-->
            <#list terminal.markdownReferenceList?filter(md -> md.type == 'HEADER') as md>
                <li class="md terminal"><a href="#${md.id}">${md.name}</a></li>
            </#list>
            <#--递归渲染组-->
            <#list terminal.groupList as group>
                <@buildGroup group=group/>
            </#list>
            <#--终端尾部Markdown-->
            <#list terminal.markdownReferenceList?filter(md -> md.type == 'FOOTER') as md>
                <li class="md terminal"><a href="#${md.id}">${md.name}</a></li>
            </#list>
        </ul>
    </#list>
    <#--文档尾部Markdown-->
    <#list doc.markdownReferenceList?filter(md -> md.type == 'FOOTER') as md>
        <li class="md doc"><a href="#${md.id}">${md.name}</a></li>
    </#list>
</ul>
