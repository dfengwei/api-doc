package com.github.dfengwei.apidoc.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.dfengwei.apidoc.domain.Group;
import com.github.dfengwei.apidoc.domain.MarkdownReference;
import com.github.dfengwei.apidoc.domain.Terminal;

import cn.hutool.core.collection.ListUtil;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dfengwei.apidoc.manager.IdUtil;

/**
 * 终端模拟器
 */
public class TerminalMocker {

    private static final List<String> terminalFolderList = ListUtil.toList("app", "web", "client");

    /**
     * 模拟终端信息对象
     * 
     * @return 终端信息对象
     */
    public static Terminal mock() {
        String terminalFolder = terminalFolderList.get(RandomUtil.randomInt(3));
        String terminalNameSuffix = RandomUtil.randomString(3);

        /* ---------- 生成终端对象 ---------- */
        String terminalName = StrUtil.format("{}-{}", terminalFolder, terminalNameSuffix);

        Terminal terminal = new Terminal();
        terminal.setId(IdUtil.get());
        terminal.setName(terminalName);
        terminal.setScanPath(new File("/xxx/yyy").toPath());

        List<MarkdownReference> markdownReferenceList = new ArrayList<>();

        MarkdownReference markdownReference = new MarkdownReference();
        markdownReference.setId(IdUtil.get());
        markdownReference.setName(StrUtil.format("终端【{}】的概述", terminalName));
        markdownReference.setFilePath(StrUtil.format("markdown/{}/overview.md", terminalFolder));
        markdownReferenceList.add(markdownReference);

        markdownReference = new MarkdownReference();
        markdownReference.setId(IdUtil.get());
        markdownReference.setName(StrUtil.format("终端【{}】的返回码", terminalName));
        markdownReference.setFilePath(StrUtil.format("markdown/{}/return-code.md", terminalFolder));
        markdownReferenceList.add(markdownReference);

        terminal.setMarkdownReferenceList(markdownReferenceList);

        terminal.setGroupList(new ArrayList<>());

        for (int i = 0; i < 5; i++) {
            Group group = generateGroup(1);
            terminal.getGroupList().add(group);
        }

        return terminal;
    }

    private static Group generateGroup(int level) {
        Group group = new Group();
        group.setSequence(RandomUtil.randomInt(10));
        group.setName(StrUtil.format("{}层组-{}", level, RandomUtil.randomString(4)));
        group.setApiList(new ArrayList<>());
        group.setChildGroupList(new ArrayList<>());

        for (int i = 0; i < RandomUtil.randomInt(10); i++) {
            group.getApiList().add(ApiMocker.mock());
        }

        if (level <= 3) {
            for (int i = 0; i < RandomUtil.randomInt(5); i++) {
                Group childGroup = generateGroup(level + 1);
                group.getChildGroupList().add(childGroup);
            }
        }

        return group;
    }

}
