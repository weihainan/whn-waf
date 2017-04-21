package com.whn.waf.common.utils;

import com.whn.waf.common.client.http.WafHttpClient;

/**
 * 工场类
 *
 * @author weihainan.
 * @since 0.1 created on 2017/3/17.
 */
public class ObjectFactory {

    private static WafHttpClient wafHttpClient;

    /**
     * Waf已有将WafHttpClient注册成Bean，请直接使用
     */
    public static WafHttpClient wafHttpClient() {
        if (wafHttpClient == null) {
            synchronized (ObjectFactory.class) {
                if (wafHttpClient == null) {
                    wafHttpClient = new WafHttpClient();
                }
            }
        }
        return wafHttpClient;
    }
}
