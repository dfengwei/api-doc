package com.github.dfengwei.apidoc.generator.html;

import java.util.HashMap;
import java.util.Map;

import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.generator.common.FreeMarker;
import com.github.dfengwei.apidoc.manager.Logger;

/**
 * 导航HTML渲染器
 *
 * @author dfengwei@163.com
 */
public class NavHtmlRenderer {

    /**
     * 渲染
     * 
     * @param doc 文档
     * @return 导航HTML
     */
    public static String generate(Doc doc) {
        // 生成数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("doc", doc);

        // 渲染
        String html = FreeMarker.render("html/nav.ftl", dataModel);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[导航渲染]渲染结果:\n{}", html);
        }

        return html;
    }

}
