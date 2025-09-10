package com.github.dfengwei.apidoc.manager;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * HTML生成器
 *
 * @author dfengwei@163.com
 */
public class IdUtil {

    private static Queue<String> queue = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            queue.add(String.valueOf(i));
        }
    }

    /**
     * 获取id
     * 
     * @return id
     */
    public static String get() {
        return queue.poll();
    }

}
