package com.cbf.framework.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换处理
 *
 * @author Frank
 */

@Slf4j
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void setDataSourceType(String dsType) {
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
