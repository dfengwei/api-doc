package com.github.dfengwei.apidoc.generator.markdown;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 引用的Markdown文档渲染器，主要对文档内的标题进行适当降级，确保其在大纲中位置的合理性
 *
 *
 * @author dfengwei@163.com
 */
public class MarkdownReferenceRenderer {

    /**
     * 标题匹配正则表达式，用于匹配Markdown中的各个级别的标题
     */
    private static final Pattern H = Pattern.compile("^\\s*#+\\s+", Pattern.MULTILINE);

    /**
     * 标题替换正则表达式，用于将所有标题降级
     */
    private static final Pattern H_REPLACE = Pattern.compile("^\\s*#", Pattern.MULTILINE);

    /**
     * 渲染
     * 
     * @param markdownContent Markdown文档内容
     * @param docLevel 文档层级，0表示顶级，可以包含任意级别的标题；1级文档只能包含2级标题（若存在1级标题，则所有标题须降一级），以此类推
     * @return 渲染后的Markdown文档内容
     */
    public static String render(String markdownContent, int docLevel) {
        if (docLevel > 0) {
            // 检查标题级别数字最小的值
            List<String> hList = ReUtil.findAll(H, markdownContent, 0);
            int minH = hList.stream().map(h -> h.trim().length()).min(Comparator.naturalOrder()).orElse(0);

            // 文档内所有标题进行降级
            if (minH <= docLevel) {
                String hn = StrUtil.repeat("#", docLevel - minH + 2);
                markdownContent = ReUtil.replaceAll(markdownContent, H_REPLACE, hn);
            }
        }

        if (Logger.isTraceEnabled()) {
            Logger.trace("[Markdown引用文件渲染]渲染结果:\n{}", markdownContent);
        }

        return markdownContent;
    }

}
