package com.zzh.riggie.common;

/**
 * 操作线程传递id给公共字段填充类
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getThreadLocal() {
        return threadLocal.get();
    }

    public static void setThreadLocal(Long id) {
        threadLocal.set(id);
    }
}
