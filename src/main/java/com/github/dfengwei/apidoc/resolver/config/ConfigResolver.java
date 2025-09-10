package com.github.dfengwei.apidoc.resolver.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.github.dfengwei.apidoc.manager.IdUtil;
import com.github.dfengwei.apidoc.manager.Logger;
import com.github.dfengwei.apidoc.domain.*;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 配置解析器，用于从配置文件中读取并解析配置
 *
 * @author dfengwei@163.com
 */
public class ConfigResolver {

    /**
     * 注释正则表达式（多行模式）
     */
    public static Pattern COMMENT = Pattern.compile("\\/\\/.*", Pattern.MULTILINE);

    /**
     * 解析配置文件
     *
     * @param configFilePath 配置文件路径
     * @return 解析配置文件生成的文档对象
     */
    public static Doc resolve(String configFilePath) {
        if (StrUtil.isBlank(configFilePath)) {
            Logger.error("[配置解析]未指定配置文件路径");
        }

        // 生成文档对象
        Doc doc = new Doc();

        // 用户当前工作目录（执行java命令的目录）
        String userDir = System.getProperty("user.dir");

        // 计算配置文件绝对路径
        configFilePath = StrUtil.join(File.separator, userDir, configFilePath);
        File configFile = FileUtil.file(configFilePath);

        // 读取配置文件
        String configFileContent;
        try {
            configFileContent = FileUtil.readUtf8String(configFile);
        } catch (IORuntimeException e) {
            Logger.error("[配置解析]配置文件读取失败, 路径:【{}】, 错误: 【{}】", configFile.getAbsolutePath(), e.getMessage());
            return null;
        }
        Logger.info("[配置解析]配置文件读取成功, 路径:【{}】", configFile.getAbsolutePath());

        // 使用正则表达式去除配置文件内容中的注释
        configFileContent = ReUtil.replaceAll(configFileContent, COMMENT, StrUtil.EMPTY);

        // 配置文本转JSON对象
        JSONObject apiDocJson = null;
        try {
            apiDocJson = JSONUtil.parseObj(configFileContent);
            if (Logger.isTraceEnabled()) {
                Logger.trace("[配置解析]配置文件JSON对象: {}", apiDocJson.toStringPretty());
            }
        } catch (Exception e) {
            Logger.error("[配置解析]配置文件内容转JSON对象失败");
        }

        /* -------------------- 解析【文档名称】 -------------------- */

        String docName = apiDocJson.getStr("docName");
        if (StrUtil.isBlank(docName)) {
            docName = FileUtil.file(userDir).getName();
            Logger.info("[配置解析]未配置文档名称, 默认使用配置文件所在项目根目录名:【{}】", docName);
        }
        doc.setDocName(docName);
        Logger.info("[配置解析]文档名称:【{}】", docName);

        /* -------------------- 解析【HTML渲染器】配置 -------------------- */

        JSONObject htmlRenderer = ObjUtil.defaultIfNull(apiDocJson.getJSONObject("htmlRenderer"), new JSONObject());
        HtmlRendererConfig htmlRendererConfig = new HtmlRendererConfig();

        // 是否启用
        if (htmlRenderer.containsKey("enable")) {
            htmlRendererConfig.setEnable(htmlRenderer.getBool("enable"));
        } else {
            htmlRendererConfig.setEnable(true);
            Logger.info("[配置解析][HTML渲染器]未配置【是否启用】, 使用默认配置:【{}】", htmlRendererConfig.getEnable());
        }

        // 是否使用单文件模式
        if (htmlRenderer.containsKey("singleFile")) {
            htmlRendererConfig.setSingleFile(htmlRenderer.getBool("singleFile"));
        } else {
            htmlRendererConfig.setSingleFile(false);
            Logger.info("[配置解析][HTML渲染器]未配置【是否使用单文件模式】, 使用默认配置:【{}】", htmlRendererConfig.getSingleFile());
        }

        // 是否使用单文件模式
        if (htmlRenderer.containsKey("compress")) {
            htmlRendererConfig.setCompress(htmlRenderer.getBool("compress"));
        } else {
            htmlRendererConfig.setCompress(true);
            Logger.info("[配置解析][HTML渲染器]未配置【是否压缩】, 使用默认配置:【{}】", htmlRendererConfig.getCompress());
        }

        // 是否使用单文件模式
        if (htmlRenderer.containsKey("path")) {
            htmlRendererConfig.setPath(htmlRenderer.getStr("path"));
        } else {
            htmlRendererConfig.setPath("out/api-doc/html");
            Logger.info("[配置解析][HTML渲染器]未配置【输出路径】, 使用默认配置:【{}】", htmlRendererConfig.getPath());
        }
        // 相对路径转绝对路径
        if (!FileUtil.isAbsolutePath(htmlRendererConfig.getPath())) {
            htmlRendererConfig.setAbsolutePath(StrUtil.join(File.separator, userDir, htmlRendererConfig.getPath()));
        }
        htmlRendererConfig.setAbsolutePath(new File(htmlRendererConfig.getAbsolutePath()).getAbsolutePath());
        Logger.info("[配置解析][HTML渲染器]输出绝对路径:【{}】", htmlRendererConfig.getAbsolutePath());

        doc.setHtmlRendererConfig(htmlRendererConfig);

        /* -------------------- 解析【Markdown渲染器】配置 -------------------- */

        JSONObject markdownRenderer =
            ObjUtil.defaultIfNull(apiDocJson.getJSONObject("markdownRenderer"), new JSONObject());
        MarkdownRendererConfig markdownRendererConfig = new MarkdownRendererConfig();

        // 是否启用
        if (markdownRenderer.containsKey("enable")) {
            markdownRendererConfig.setEnable(markdownRenderer.getBool("enable"));
        } else {
            markdownRendererConfig.setEnable(true);
            Logger.info("[配置解析][Markdown渲染器]未配置【是否启用】, 使用默认配置:【{}】", markdownRendererConfig.getEnable());
        }

        // 是否使用单文件模式
        if (markdownRenderer.containsKey("path")) {
            markdownRendererConfig.setPath(markdownRenderer.getStr("path"));
        } else {
            markdownRendererConfig.setPath("out/api-doc/markdown");
            Logger.info("[配置解析][Markdown渲染器]未配置【输出路径】, 使用默认配置:【{}】", markdownRendererConfig.getPath());
        }
        // 相对路径转绝对路径
        if (!FileUtil.isAbsolutePath(markdownRendererConfig.getPath())) {
            markdownRendererConfig
                .setAbsolutePath(StrUtil.join(File.separator, userDir, markdownRendererConfig.getPath()));
        }
        markdownRendererConfig.setAbsolutePath(new File(htmlRendererConfig.getAbsolutePath()).getAbsolutePath());
        Logger.info("[配置解析][Markdown渲染器]输出绝对路径:【{}】", markdownRendererConfig.getAbsolutePath());

        doc.setMarkdownRendererConfig(markdownRendererConfig);

        /* ---------- 解析Markdown引用文件列表 ---------- */

        doc.setMarkdownReferenceList(new ArrayList<>());
        if (apiDocJson.containsKey("markdownList")) {
            JSONArray markdownArray = apiDocJson.getJSONArray("markdownList");
            for (int i = 0; i < markdownArray.size(); i++) {
                JSONObject markdownResourceJson = markdownArray.getJSONObject(i);
                MarkdownReference markdownReference = new MarkdownReference();

                String name = markdownResourceJson.getStr("name");
                String type = markdownResourceJson.getStr("type");
                String filePath = markdownResourceJson.getStr("filePath");

                if (FileUtil.isFile(filePath)) {
                    Logger.warn("[配置解析][Markdown文件引用]引用文件不存在:【{}】", filePath);
                    continue;
                }

                if (StrUtil.isBlank(name)) {
                    name = FileUtil.getName(filePath);
                    Logger.info("[配置解析][Markdown文件引用]未配置名称, 使用文件名作为名称:【{}】", filePath);
                }

                if (StrUtil.isBlank(type)) {
                    type = "HEADER";
                } else {
                    if (StrUtil.containsAnyIgnoreCase(type, "HEADER", "FOOTER")) {
                        type = type.toUpperCase();
                    } else {
                        Logger.warn("[配置解析][Markdown文件引用]类型错误:【{}】", type);
                        continue;
                    }
                }

                markdownReference.setId(IdUtil.get());
                markdownReference.setName(name);
                markdownReference.setType(type);
                markdownReference.setFilePath(filePath);
                doc.getMarkdownReferenceList().add(markdownReference);
            }
        }

        /* ---------- 解析终端配置列表 ---------- */

        List<Terminal> terminalList = new ArrayList<>();
        JSONArray terminalArray = apiDocJson.getJSONArray("terminalList");
        if (CollUtil.isEmpty(terminalArray)) {
            Logger.warn("[配置解析]未找到任何终端配置");
        } else {
            for (int i = 0; i < terminalArray.size(); i++) {
                JSONObject terminalJson = terminalArray.getJSONObject(i);
                terminalList.add(resolveTerminal(terminalJson));
            }
            doc.setTerminalList(terminalList);
        }

        return doc;
    }

    /**
     * 解析终端
     *
     * @param terminalJson 终端JSON
     * @return 解析终端配置生成的终端对象
     */
    public static Terminal resolveTerminal(JSONObject terminalJson) {
        Terminal terminal = new Terminal();
        terminal.setId(IdUtil.get());

        // 用户当前工作目录（执行java命令的目录）
        String userDir = System.getProperty("user.dir");

        // 解析终端名称
        String name = terminalJson.getStr("name");
        if (StrUtil.isBlank(name)) {
            String scanPath = terminalJson.getStr("scanPath");
            name = FileUtil.getName(scanPath);

            Logger.info("[配置解析]未配置名称扫描路径: {}, 使用使用扫描路径作为名称:【{}】", scanPath, terminal.getName());
        }
        terminal.setName(name);

        /* ---------- 解析Markdown引用文件列表 ---------- */

        terminal.setMarkdownReferenceList(new ArrayList<>());
        if (terminalJson.containsKey("markdownList")) {
            JSONArray markdownArray = terminalJson.getJSONArray("markdownList");
            for (int i = 0; i < markdownArray.size(); i++) {
                JSONObject markdownResourceJson = markdownArray.getJSONObject(i);
                MarkdownReference markdownReference = new MarkdownReference();

                String markdownName = markdownResourceJson.getStr("name");
                String type = markdownResourceJson.getStr("type");
                String filePath = markdownResourceJson.getStr("filePath");

                if (FileUtil.isFile(filePath)) {
                    Logger.warn("[配置解析][终端:{}][Markdown文件引用]引用文件不存在:【{}】", filePath);
                    continue;
                }

                if (StrUtil.isBlank(markdownName)) {
                    markdownName = FileUtil.getName(filePath);
                    Logger.info("[配置解析][终端:{}][Markdown文件引用]未配置名称, 使用文件名作为名称:【{}】", filePath);
                }

                if (StrUtil.isBlank(type)) {
                    type = "HEADER";
                } else {
                    if (StrUtil.containsAnyIgnoreCase(type, "HEADER", "FOOTER")) {
                        type = type.toUpperCase();
                    } else {
                        Logger.warn("[配置解析][终端:{}][Markdown文件引用]类型错误:【{}】", name, type);
                        continue;
                    }
                }

                markdownReference.setId(IdUtil.get());
                markdownReference.setName(markdownName);
                markdownReference.setType(type);
                markdownReference.setFilePath(filePath);
                terminal.getMarkdownReferenceList().add(markdownReference);
            }
        }

        /* ---------- 分组列表 ---------- */

        terminal.setGroupList(new ArrayList<>());

        /* ---------- 解析扫描路径（Controller文件所在路径） ---------- */

        String scanPath = terminalJson.getStr("scanPath");
        if (StrUtil.isBlank(scanPath)) {
            Logger.error("[配置解析][终端:{}]缺失扫描路径", name);
        }
        // 扫描路径非绝对路径，则表示扫描路径为项目根路径下的相对路径
        if (!FileUtil.isAbsolutePath(scanPath)) {
            scanPath = StrUtil.join(File.separator, userDir, scanPath);
        }
        File scanPathFile = FileUtil.file(scanPath);

        // 路径有效性检查
        if (FileUtil.isDirectory(scanPathFile)) {
            terminal.setScanPath(scanPathFile.toPath());
            Logger.info("[配置解析][终端:{}]有效的扫描路径:【{}】", name, scanPathFile.getAbsolutePath());
        } else {
            Logger.error("[配置解析][终端:{}]无效的扫描路径:【{}】", name, scanPathFile.getAbsolutePath());
        }

        return terminal;
    }

}
