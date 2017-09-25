package com.whn.waf.common.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;
import java.util.Map;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/25
 */
public class FastJsonMapper {

    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteEnumUsingName,
            SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
//            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
//            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
    };


    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }


    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将string转化为序列化的json字符串
     *
     * @return
     */
    public static Object textToJson(String text) {
        Object objectJson = JSON.parse(text);
        return objectJson;
    }

    /**
     * json字符串转化为map
     *
     * @param s
     * @return
     */
    public static Map<String, Object> toMap(String s) {
        Map<String, Object> m = JSONObject.parseObject(s);
        return m;
    }

    /**
     * 将对象转化为string
     *
     * @return
     */
    public static String toJson(Object object) {
        String s = JSONObject.toJSONString(object);
        return s;
    }

    /**
     * <p>将json字符串转换成对象.采用Fastjson工具---自动识别日期时间类型转换</p>
     *
     * @param json  json字符串
     * @param clazz 源对象
     */
    public static Object toBean(String json, Object clazz) {
        if (StringUtils.isBlank(json) || clazz == null) {
            return null;
        }
        return JSON.parseObject(json, clazz.getClass());
    }
}
