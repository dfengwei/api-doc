<#list doc.markdownReferenceList?filter(md -> md.type == 'HEADER') as md>
    ${markdownMap[md.id]}<#lt>
</#list>

<#list terminalList as terminal>
    # ${terminal.name}<#lt>
    ${terminalContentMap[terminal.id]}<#lt>
</#list>

<#list doc.markdownReferenceList?filter(md -> md.type == 'FOOTER') as md>
    ${markdownMap[md.id]}<#lt>
</#list>

文档生成时间：${renderTime}