package com.github.dfengwei.apidoc.generator.html;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.domain.MarkdownReference;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.generator.common.CommonMark;
import com.github.dfengwei.apidoc.generator.common.FreeMarker;
import com.github.dfengwei.apidoc.generator.common.OuterResourceUtil;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.StrUtil;

/**
 * 导航HTML渲染器
 *
 * @author dfengwei@163.com
 */
public class DocHtmlRenderer {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 渲染
     * 
     * @param doc 文档
     * @return 导航HTML
     */
    public static String generate(Doc doc) {
        // Markdown渲染出的HTML map
        Map<String, String> markdownMap = new HashMap<>();

        // 渲染CSS
        String css = FreeMarker.render("html/css.css", null);
        if (doc.getHtmlRendererConfig().getCompress()) {
            css = Compressor.compressCss(css);
        }

        // 渲染JS
        String js = FreeMarker.render("html/js.js", null);
        if (doc.getHtmlRendererConfig().getCompress()) {
            js = Compressor.compressJs(js);
        }

        // 渲染Markdown引用文件HTML内容
        String html;
        for (MarkdownReference md : doc.getMarkdownReferenceList()) {
            String markdown = OuterResourceUtil.read(md.getFilePath());
            html = StrUtil.isEmpty(markdown) ? "" : CommonMark.render(markdown);
            markdownMap.put(md.getId(), html);
        }

        // 渲染各个终端HTML
        Map<String, String> terminalHtmlMap = new HashMap<>();
        for (Terminal terminal : doc.getTerminalList()) {
            String terminalHtml = TerminalHtmlRenderer.generate(terminal);
            terminalHtmlMap.put(terminal.getId(), terminalHtml);
        }

        // 生成数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("doc", doc);
        dataModel.put("css", css);
        dataModel.put("js", js);
        dataModel.put("markdownMap", markdownMap);
        dataModel.put("terminalHtmlMap", terminalHtmlMap);
        dataModel.put("renderTime", LocalDateTime.now(ZoneId.of("+8")).format(DTF));

        // 渲染
        html = FreeMarker.render("html/doc.ftl", dataModel);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[文档渲染]渲染结果:\n{}", html);
        }

        return html;
    }

}
