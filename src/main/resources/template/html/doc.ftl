<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${doc.docName}</title>
    <#if doc.htmlRendererConfig.singleFile>
        <style>
            <#include "assets/github-markdown-light.5.8.1.min.css" parse=false>
        </style>
    <#else>
        <link rel="stylesheet" href="github-markdown-light.5.8.1.min.css">
    </#if>
    <style type="text/tailwindcss">
        ${css}
    </style>
    <#if doc.htmlRendererConfig.singleFile>
        <script>
            <#include "assets/jquery-3.7.1.min.js" parse=false>
        </script>
        <script>
            <#include "assets/tailwindcss-browser.4.1.13.min.js" parse=false>
        </script>
    <#else>
        <script src="jquery-3.7.1.min.js"></script>
        <script src="tailwindcss-browser.4.1.13.min.js"></script>
    </#if>
    <script>
        ${js}
    </script>
</head>
<body>
<aside id="aside">
    <#include "nav.ftl">
</aside>
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
<main id="main">
    <#--文档头部Markdown-->
    <#assign headerMdList = doc.markdownReferenceList?filter(md -> md.type == 'HEADER')>
    <#list headerMdList>
        <div class="<@calMdClass childQuantity=headerMdList?size/>">
            <#items as md>
                <div id="${md.id}" class="md markdown-body">
                    ${markdownMap[md.id]}
                </div>
            </#items>
        </div>
    </#list>

    <#list doc.terminalList>
        <#items as terminal>
            ${terminalHtmlMap[terminal.id]}
        </#items>
    </#list>

    <#--文档尾部Markdown-->
    <#assign footerMdList = doc.markdownReferenceList?filter(md -> md.type == 'FOOTER')>
    <#list footerMdList>
        <div class="<@calMdClass childQuantity=footerMdList?size/>">
            <#items as md>
                <div id="${md.id}" class="md markdown-body">
                    ${markdownMap[md.id]}
                </div>
            </#items>
        </div>
    </#list>

    <#--页脚-->
    <div class="p-3 text-gray-400">文档生成时间：${renderTime}</div>
</main>
</body>
</html>