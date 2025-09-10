package com.github.dfengwei.apidoc.generator.common;

import java.io.StringWriter;
import java.util.Map;

import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FreeMarker
 *
 * @author dfengwei@163.com
 */
public class FreeMarker {

    private static Configuration configuration;

    static {
        try {
            configuration = new Configuration(Configuration.VERSION_2_3_34);
            configuration.setClassLoaderForTemplateLoading(FreeMarker.class.getClassLoader(), "template");
            configuration.setDefaultEncoding("utf-8");

            if (Logger.isDebugEnabled()) {
                Logger.debug("FreeMarker初始化成功, 版本: {}", Configuration.getVersion());
            }
        } catch (Exception e) {
            Logger.error("FreeMarker初始化失败", e);
        }
    }

    /**
     * 根据模版和数据模型渲染
     *
     * @param templateName 模版名称
     * @param dataModel 数据模型
     * @return 渲染出的文本
     */
    public static String render(String templateName, Map<String, Object> dataModel) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            Logger.error("FreeMaker渲染失败, 错误: {}", e.getMessage());
            return StrUtil.EMPTY;
        }
    }

}
