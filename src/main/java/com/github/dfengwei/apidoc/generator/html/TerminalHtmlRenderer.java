package com.github.dfengwei.apidoc.generator.html;

import java.util.HashMap;
import java.util.Map;

import com.github.dfengwei.apidoc.domain.MarkdownReference;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.generator.common.CommonMark;
import com.github.dfengwei.apidoc.generator.common.FreeMarker;
import com.github.dfengwei.apidoc.generator.common.OuterResourceUtil;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.StrUtil;

/**
 * 终端HTML渲染器
 *
 * @author dfengwei@163.com
 */
public class TerminalHtmlRenderer {

    /**
     * 渲染
     * 
     * @param terminal 终端
     * @return 终端渲染生成的HTML
     */
    public static String generate(Terminal terminal) {
        // Markdown渲染出的HTML map
        Map<String, String> markdownMap = new HashMap<>();

        // 渲染Markdown引用文件HTML内容
        for (MarkdownReference md : terminal.getMarkdownReferenceList()) {
            String markdown = OuterResourceUtil.read(md.getFilePath());
            String html = StrUtil.isEmpty(markdown) ? "" : CommonMark.render(markdown);
            markdownMap.put(md.getId(), html);
        }

        // 生成数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("terminal", terminal);
        dataModel.put("markdownMap", markdownMap);

        // 渲染
        String html = FreeMarker.render("html/terminal.ftl", dataModel);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[终端渲染]渲染结果:\n{}", html);
        }

        return html;
    }

}
