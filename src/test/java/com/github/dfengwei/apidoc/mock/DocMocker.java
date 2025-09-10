package com.github.dfengwei.apidoc.mock;

import java.util.ArrayList;
import java.util.List;


import com.github.dfengwei.apidoc.domain.*;
import com.github.dfengwei.apidoc.manager.IdUtil;

/**
 * 文档模拟器
 */
public class DocMocker {

    /**
     * 模拟终端信息对象
     * 
     * @return 终端信息对象
     */
    public static Doc mock() {
        Doc doc = new Doc();
        doc.setDocName("xxx项目接口文档");

        HtmlRendererConfig HtmlRendererConfig = new HtmlRendererConfig();
        HtmlRendererConfig.setCompress(false);
        HtmlRendererConfig.setSingleFile(false);
        HtmlRendererConfig.setPath("out/doc/html");
        doc.setHtmlRendererConfig(HtmlRendererConfig);

        MarkdownRendererConfig markdownRendererConfig = new MarkdownRendererConfig();
        markdownRendererConfig.setPath("out/doc/markdown");
        doc.setMarkdownRendererConfig(markdownRendererConfig);

        List<MarkdownReference> markdownReferenceList = new ArrayList<>();

        MarkdownReference markdownReference = new MarkdownReference();
        markdownReference.setId(IdUtil.get());
        markdownReference.setName("文档概述");
        markdownReference.setFilePath("markdown/overview.md");
        markdownReferenceList.add(markdownReference);

         markdownReference = new MarkdownReference();
        markdownReference.setId(IdUtil.get());
        markdownReference.setName("文档返回码");
        markdownReference.setFilePath("markdown/return-code.md");
        markdownReferenceList.add(markdownReference);

        doc.setMarkdownReferenceList(markdownReferenceList);

        List<Terminal> terminalList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            terminalList.add(TerminalMocker.mock());
        }
        doc.setTerminalList(terminalList);

        return doc;
    }

}
