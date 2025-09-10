package com.github.dfengwei.apidoc.generator.markdown;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.domain.MarkdownReference;
import com.github.dfengwei.apidoc.domain.MarkdownRendererConfig;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.generator.common.FreeMarker;
import com.github.dfengwei.apidoc.generator.common.OuterResourceUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Markdown生成器
 *
 * @author dfengwei@163.com
 */
public class MarkdownGenerator {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 生成
     * 
     * @param doc 文档对象
     */
    public static void generate(Doc doc) {
        long startTime = System.currentTimeMillis();

        MarkdownRendererConfig markdownRendererConfig = doc.getMarkdownRendererConfig();
        if (markdownRendererConfig == null) {
            return;
        }

        // Markdown文件渲染出的内容的map
        Map<String, String> markdownMap = new HashMap<>();

        // 渲染Markdown引用文件HTML内容
        for (MarkdownReference md : doc.getMarkdownReferenceList()) {
            String markdown = OuterResourceUtil.read(md.getFilePath());
            markdown = StrUtil.isEmpty(markdown) ? "" : MarkdownReferenceRenderer.render(markdown, 0);
            markdownMap.put(md.getId(), markdown);
        }

        // 渲染各个终端Markdown
        List<Terminal> terminalList = new ArrayList<>();
        Map<String, String> terminalContentMap = new HashMap<>();
        for (Terminal terminal : doc.getTerminalList()) {
            terminalList.add(terminal);
            String terminalMarkdown = TerminalMarkdownRenderer.generate(terminal);
            terminalContentMap.put(terminal.getId(), terminalMarkdown);
        }

        // 生成数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("doc", doc);
        dataModel.put("markdownMap", markdownMap);
        dataModel.put("terminalList", terminalList);
        dataModel.put("terminalContentMap", terminalContentMap);
        dataModel.put("renderTime", LocalDateTime.now(ZoneId.of("+8")).format(DTF));

        // 渲染
        String markdown = FreeMarker.render("markdown/doc.ftl", dataModel);

        // 保存为<docName>.md
        File markdownFile = FileUtil.file(markdownRendererConfig.getPath(), doc.getDocName() + ".md");
        FileUtil.writeUtf8String(markdown, markdownFile);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[Markdown生成]生成结果:\n{}", markdown);
        }

        Logger.info("[Markdown生成]生成成功, 耗时: {}", DateUtil.formatBetween(System.currentTimeMillis() - startTime));
    }

}
