package com.whn.waf.common.base.support;

import org.springframework.core.NamedThreadLocal;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/5/26.
 */
public class AppNameProvider {

    private static final ThreadLocal<String> NAME = new NamedThreadLocal<>("appName");

    public static void set(String application) {
        NAME.set(application);
    }

    public static String getApplication() {
        return NAME.get();
    }

    public static void remove() {
        NAME.remove();
    }
}
