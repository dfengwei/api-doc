package com.github.dfengwei.apidoc;

import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.generator.html.HtmlGenerator;
import com.github.dfengwei.apidoc.generator.markdown.MarkdownGenerator;
import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.resolver.comment.SourceFileScanner;
import com.github.dfengwei.apidoc.resolver.config.ConfigResolver;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 生成器
 *
 * @author dfengwei@163.com
 */
public class Generator {

    /**
     * 入口
     * 
     * @param args 参数
     */
    public static void main(String[] args) {
        try {
            Logger.info("[配置解析]开始生成接口文档...");
            long startTime = System.currentTimeMillis();

            // 获取【配置文件路径】参数
            String configFilePath = ArrayUtil.get(args, 0);
            if (StrUtil.isBlank(configFilePath)) {
                configFilePath = "src/main/resources/api-doc.json";
                Logger.info("[配置解析]未指定配置文件路径, 使用默认值:【{}】", configFilePath);
            }

            // 获取【日志级别】调用参数
            String logLevel = ArrayUtil.get(args, 1);
            if (StrUtil.isBlank(logLevel)) {
                logLevel = "INFO";
                Logger.info("[配置解析]未指定日志级别, 使用默认值:【{}】", logLevel);
            }
            Logger.setLevel(logLevel);

            // 解析配置文件
            Doc doc = ConfigResolver.resolve(configFilePath);
            if (doc == null) {
                Logger.error("[配置解析]接口文档生成失败, 耗时: {}", DateUtil.formatBetween(System.currentTimeMillis() - startTime));
                return;
            }

            if (CollUtil.isEmpty(doc.getTerminalList())) {
                Logger.info("[配置解析]终端列表为空, 未生成任何文档, 耗时: {}",
                    DateUtil.formatBetween(System.currentTimeMillis() - startTime));
                return;
            }

            // 扫描源码文件
            SourceFileScanner.scan(doc);

            // 生成HTML
            HtmlGenerator.generate(doc);

            // 生成Markdown
            MarkdownGenerator.generate(doc);

            Logger.info("[配置解析]接口文档生成成功, 耗时: {}", DateUtil.formatBetween(System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }
    }

}
