package com.github.dfengwei.apidoc.generator.markdown;

import java.util.HashMap;
import java.util.Map;

import com.github.dfengwei.apidoc.domain.MarkdownReference;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.generator.common.FreeMarker;
import com.github.dfengwei.apidoc.generator.common.OuterResourceUtil;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.StrUtil;

/**
 * 终端Markdown渲染器
 *
 * @author dfengwei@163.com
 */
public class TerminalMarkdownRenderer {

    /**
     * 渲染
     * 
     * @param terminal 终端对象
     * @return 终端Markdown内容
     */
    public static String generate(Terminal terminal) {
        // Markdown渲染出的HTML map
        Map<String, String> markdownMap = new HashMap<>();

        // 渲染Markdown引用文件HTML内容
        for (MarkdownReference md : terminal.getMarkdownReferenceList()) {
            String markdown = OuterResourceUtil.read(md.getFilePath());
            markdown = StrUtil.isEmpty(markdown) ? "" : MarkdownReferenceRenderer.render(markdown, 1);
            markdownMap.put(md.getId(), markdown);
        }

        // 生成数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("terminal", terminal);
        dataModel.put("markdownMap", markdownMap);

        // 渲染并返回
        String markdown = FreeMarker.render("markdown/terminal.ftl", dataModel);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[Markdown终端渲染]渲染结果:\n{}", markdown);
        }

        return markdown;
    }

}
