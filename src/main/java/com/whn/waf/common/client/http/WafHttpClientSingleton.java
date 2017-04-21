package com.whn.waf.common.client.http;

import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.util.Collection;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public final class WafHttpClientSingleton {
    private static final WafHttpClientSingleton instance = new WafHttpClientSingleton();
    private HttpClient httpClient;
    private WafHttpClientBuilder wafHttpClientBuilder;

    private WafHttpClientSingleton() {
        wafHttpClientBuilder = new WafHttpClientBuilder();
        httpClient = wafHttpClientBuilder.build();
    }

    public static WafHttpClientSingleton getInstance() {
        return instance;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    //TODO:每次调用都会创建一个新的HttpClient和本类的语义不符
    public HttpClient getHttpClient(int retryCount) {
        return wafHttpClientBuilder.build(retryCount);
    }

    //TODO:每次调用都会创建一个新的HttpClient和本类的语义不符
    public HttpClient getHttpClient(int retryCount, Collection<Class<? extends IOException>> clazzes) {
        return wafHttpClientBuilder.build(retryCount, clazzes);
    }
}
