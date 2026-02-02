package com.github.dfengwei.apidoc.resolver.comment;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.github.dfengwei.apidoc.domain.Api;
import com.github.dfengwei.apidoc.domain.Doc;
import com.github.dfengwei.apidoc.domain.Group;
import com.github.dfengwei.apidoc.domain.Terminal;
import com.github.dfengwei.apidoc.manager.IdUtil;
import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 源码文件扫描器
 *
 * @author dfengwei@163.com
 */
public class SourceFileScanner {

    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    /**
     * 解析
     *
     * @param doc 文档
     */
    public static void scan(Doc doc) {
        long startTime = System.currentTimeMillis();
        Logger.info("[源码文件扫描]扫描开始...");

        // 扫描的掩码文件数量
        int sourceFileCount = 0;

        // 遍历所有终端
        for (Terminal terminal : doc.getTerminalList()) {
            if (terminal.getScanPath() == null) {
                continue;
            }

            // 遍历扫描路径下的所有文件，获取所有接口文档（注释内容），过滤隐藏文件（版本控制软件会携带隐藏文件）
            List<File> sourceFileList = FileUtil.loopFiles(terminal.getScanPath(), f -> {
                if (f.isHidden()) {
                    // 跳过隐藏的目录/文件
                    return false;
                } else if (f.isDirectory() && f.getName().startsWith(".")) {
                    // 跳过.开头的目录
                    return false;
                } else if (!f.isDirectory() && !f.getName().endsWith(".java")) {
                    // 跳过非.java文件
                    return false;
                }
                return true;
            });

            // 过滤隐藏文件（版本控制软件会携带隐藏文件）
            sourceFileList = sourceFileList.stream().filter(file -> !file.isHidden()).collect(Collectors.toList());

            if (Logger.isInfoEnabled()) {
                Logger.info("[终端解析:{}]扫描到{}个源码文件...", terminal.getName(), sourceFileList.size());
            }

            for (int i = 0; i < sourceFileList.size(); i++) {
                sourceFileCount++;
                File sourceFile = sourceFileList.get(i);

                // 读取文件内容
                String sourceFileContent = FileUtil.readUtf8String(sourceFile);

                // 截取注释，这里不使用正则表达式，容易造成堆栈溢出
                String[] comments = StrUtil.subBetweenAll(sourceFileContent, "/**", "*/");
                List<String> commentList =
                    Arrays.stream(comments).filter(c -> c.contains("@api")).collect(Collectors.toList());

                if (Logger.isInfoEnabled()) {
                    Logger.info("[终端解析:{}]从第{}个源码文件:【{}】解析出{}个接口", terminal.getName(), (i + 1),
                        sourceFile.getAbsolutePath(), commentList.size());
                }

                // 遍历各个方法上的注释，并进行解析
                for (String comment : commentList) {
                    // \r\n转成\n
                    comment = StrUtil.replace(comment, StrUtil.CRLF, StrUtil.LF);

                    // 生成接口明细
                    Api api = new Api();

                    // 为每个接口明细生成一个唯一ID
                    api.setId(IdUtil.get());

                    // 设置源码文件路径
                    api.setSourceFilePath(sourceFile.getAbsolutePath());

                    // 设置注释内容
                    api.setComment(comment);

                    // 解析
                    ApiResolver.resolve(terminal, api);

                    // 接口是否禁用
                    if (api.getDisabled()) {
                        continue;
                    }

                    // 找到接口所属组对象，并将接口对象放入该组
                    List<Group> groupList = terminal.getGroupList();
                    Group belongToGroup = null;
                    for (Group apiGroup : api.getGroupList()) {
                        boolean groupExist = false;
                        for (Group group : groupList) {
                            if (StrUtil.equals(group.getName(), apiGroup.getName())) {
                                if (!group.getSequence().equals(apiGroup.getSequence())) {
                                    Logger.info("[终端解析:{}]分组名:【{}:{}】与分组名【{}:{}】序号不一致, 将使用分组:【{}:{}】中的序号",
                                        terminal.getName(), group.getSequence(), group.getName(),
                                        apiGroup.getSequence(), apiGroup.getName(), group.getSequence(),
                                        group.getName());
                                }

                                belongToGroup = group;
                                groupList = belongToGroup.getChildGroupList();
                                groupExist = true;
                                break;
                            }
                        }

                        if (!groupExist) {
                            belongToGroup = new Group();
                            belongToGroup.setName(apiGroup.getName());
                            belongToGroup.setSequence(apiGroup.getSequence());
                            belongToGroup.setApiList(new ArrayList<>());
                            belongToGroup.setChildGroupList(new ArrayList<>());

                            groupList.add(belongToGroup);
                            ListUtil.sort(groupList, (g1, g2) -> {
                                if (!g1.getSequence().equals(g2.getSequence())) {
                                    // 序号不同，按序号排序
                                    return g1.getSequence() - g2.getSequence();
                                } else {
                                    // 序号相同，按中文排序
                                    return COLLATOR.compare(g1.getName(), g2.getName());
                                }
                            });

                            groupList = belongToGroup.getChildGroupList();
                        }
                    }
                    belongToGroup.getApiList().add(api);
                    ListUtil.sort(belongToGroup.getApiList(), (api1, api2) -> {
                        if (!api1.getSequence().equals(api2.getSequence())) {
                            // 序号不同，按序号排序
                            return api1.getSequence() - api2.getSequence();
                        } else {
                            // 序号相同，按中文排序
                            return COLLATOR.compare(api1.getName(), api2.getName());
                        }
                    });
                }
            }
        }

        long endTime = System.currentTimeMillis();
        Logger.info("[源码文件扫描]扫描完成, 共扫描{}个源码文件, 耗时: {}", sourceFileCount, DateUtil.formatBetween(endTime - startTime));
    }

}
