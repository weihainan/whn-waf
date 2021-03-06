package com.whn.waf.common.client;


import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * 类功能说明：httpclient工具类,基于httpclient 4.x
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * post请求
     *
     * @param url
     * @param formParams
     * @return
     */
    public static String doPost(String url, Map<String, String> formParams) {
        if (MapUtils.isEmpty(formParams)) {
            return doPost(url);
        }
        try {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            for (String key : formParams.keySet()) {
                requestEntity.add(key, MapUtils.getString(formParams, key, ""));
            }
            return RestClient.getClient().postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }
        return "";
    }

    public static <T> T doPost(String url, Object body, Class<T> resType, Object ... urlParam){
        return RestClient.getClient().postForObject(url, body, resType, urlParam);
    }

    /**
     * post请求
     */
    public static String doPost(String url) {
        try {
            return RestClient.getClient().postForObject(url, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }

        return "";
    }

    /**
     * get请求
     */
    public static String doGet(String url) {
        try {
            return RestClient.getClient().getForObject(url, String.class);
        } catch (Exception e) {
            LOGGER.error("GET请求出错：{}", url, e);
        }

        return "";
    }

}