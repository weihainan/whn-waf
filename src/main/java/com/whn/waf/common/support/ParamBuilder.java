package com.whn.waf.common.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建拥有多个值的Map(简化map操作):<br/>
 * map = ParamBuilder.of().withParam().withParam().withParam().build();.
 *
 * @author weihainan.
 * @since 0.1 created on 2017/2/8.
 */
public final class ParamBuilder {

    private Map<String, Object> paramMap = new HashMap<>(8);

    private ParamBuilder() {
        // empty
    }

    /**
     * 获取ParamBuilder对象.
     */
    public static ParamBuilder of(){
        return new ParamBuilder();
    }

    /**
     * 获取ParamBuilder对象并设置一对key-value.
     */
    public static ParamBuilder of(String paramName, Object paramValue) {
        return new ParamBuilder().withParam(paramName, paramValue);
    }

    /**
     * 设置参数，可多次操作.
     */
    public ParamBuilder withParam(String paramName, Object paramValue) {
        paramMap.put(paramName, paramValue);
        return this;
    }

    /**
     * 获取最终的map.
     */
    public Map<String, Object> build() {
        return paramMap;
    }
}
