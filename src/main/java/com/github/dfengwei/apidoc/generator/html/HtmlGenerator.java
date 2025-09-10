package com.github.dfengwei.apidoc.generator.html;

import java.io.File;

import org.jsoup.Jsoup;

import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.domain.HtmlRendererConfig;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;

/**
 * HTML生成器
 *
 * @author dfengwei@163.com
 */
public class HtmlGenerator {

    /**
     * 生成
     * 
     * @param doc 文档对象
     */
    public static void generate(Doc doc) {
        // 记录生成开始时间
        long startTime = System.currentTimeMillis();

        // 文档渲染成HTML
        String html = DocHtmlRenderer.generate(doc);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[HTML渲染]渲染结果: {}\n", html);
        }

        if (doc.getHtmlRendererConfig().getCompress()) {
            html = Compressor.compressHtml(html);

            if (Logger.isTraceEnabled()) {
                Logger.trace("[HTML压缩]压缩结果: {}\n", html);
            }
        } else {
            html = Jsoup.parse(html).html();

            if (Logger.isTraceEnabled()) {
                Logger.trace("[HTML美化]美化结果: {}\n", html);
            }
        }

        // 保存为index.html
        HtmlRendererConfig htmlRendererConfig = doc.getHtmlRendererConfig();
        File indexHtmlFile = FileUtil.file(htmlRendererConfig.getPath(), "index.html");
        FileUtil.writeUtf8String(html, indexHtmlFile);

        // 复制index.html关联的资源文件
        if (!doc.getHtmlRendererConfig().getSingleFile()) {
            FileUtil.writeFromStream(ResourceUtil.getStream("template/html/assets/github-markdown-light.5.8.1.min.css"),
                FileUtil.file(doc.getHtmlRendererConfig().getAbsolutePath(), "github-markdown-light.5.8.1.min.css")
                    .getPath());
            FileUtil.writeFromStream(ResourceUtil.getStream("template/html/assets/jquery-3.7.1.min.js"),
                FileUtil.file(doc.getHtmlRendererConfig().getAbsolutePath(), "jquery-3.7.1.min.js").getPath());
            FileUtil.writeFromStream(ResourceUtil.getStream("template/html/assets/tailwindcss-browser.4.1.13.min.js"),
                FileUtil.file(doc.getHtmlRendererConfig().getAbsolutePath(), "tailwindcss-browser.4.1.13.min.js")
                    .getPath());
        }

        if (Logger.isTraceEnabled()) {
            Logger.trace("[HTML生成]生成内容:\n{}", html);
        }

        Logger.info("[HTML生成]生成成功, 耗时: {}", DateUtil.formatBetween(System.currentTimeMillis() - startTime));
    }

}
