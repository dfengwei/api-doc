package com.github.dfengwei.apidoc.generator.common;

import java.io.File;

import com.github.dfengwei.apidoc.manager.Logger;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 外部资源文件工具
 *
 * @author dfengwei@163.com
 */

public class OuterResourceUtil {

    /**
     * 读取
     *
     * @param resourcePath 资源文件在项目中的相对路径，例如：src/main/resources/markdown/xxx.md
     * @return 资源文件内容
     */
    public static String read(String resourcePath) {
        // 用户当前工作目录（执行java命令的目录）
        String userDir = System.getProperty("user.dir");

        // 资源绝对路径
        String resourceAbsolutePath = StrUtil.join(File.separator, userDir, resourcePath);

        if (!FileUtil.exist(resourceAbsolutePath)) {
            Logger.error("资源文件读取失败, 不存在的资源路径: {}", resourcePath);
            return null;
        }

        return FileUtil.readUtf8String(resourceAbsolutePath);
    }

}
