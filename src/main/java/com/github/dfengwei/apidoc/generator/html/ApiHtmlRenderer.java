package com.github.dfengwei.apidoc.generator.html;

import java.util.HashMap;
import java.util.Map;

import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.generator.common.FreeMarker;
import com.github.dfengwei.apidoc.manager.Logger;

/**
 * 接口HTML渲染器
 *
 * @author dfengwei@163.com
 */
public class ApiHtmlRenderer {

    /**
     * 渲染
     * 
     * @param api 接口对象
     * @return 接口HTML
     */
    public static String render(Api api) {
        // 生成数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("api", api);

        // 渲染
        String html = FreeMarker.render("html/api.ftl", dataModel);

        if (Logger.isTraceEnabled()) {
            Logger.trace("[接口渲染]渲染结果:\n{}", html);
        }

        return html;
    }

}
