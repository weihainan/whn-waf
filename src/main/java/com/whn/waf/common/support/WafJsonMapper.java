package com.whn.waf.common.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
public class WafJsonMapper {

    private static final Logger logger = LoggerFactory.getLogger(WafJsonMapper.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 设置将驼峰命名法转换成下划线的方式输入输出
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //设置时区
        mapper.setTimeZone(TimeZone.getDefault());
        // 设置时间为 ISO-8601 日期
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        // map的空值不写出
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        // 序列化BigDecimal时之间输出原始数字还是科学计数，默认false，即是否以toPlainString()科学计数方式来输出
        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, false);
        //允许单引号
        //mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //设定是否使用Enum的toString函数来读取Enum, 为False时使用Enum的name()函数来读取Enum,
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        // 不允许使用数字作为枚举
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 如果输入不存在的字段时不会报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 使用默认的Jsckson注解
        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector());

        // 接受单个值作为数组
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public WafJsonMapper() {
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static void setMapper(ObjectMapper mapperValue) {
        mapper = mapperValue;
    }

    public static <T> T parse(String json, Class<T> objectType) throws IOException {
        if (json == null) {
            return null;
        } else {
            Assert.notNull(objectType, "objectType cannot be null.");
            return mapper.readValue(json, objectType);
        }
    }

    public static <T> T parse(InputStream stream, Class<T> objectType) throws IOException {
        Assert.notNull(stream, "stream cannot be null.");
        Assert.notNull(objectType, "objectType cannot be null.");
        return mapper.readValue(stream, objectType);
    }

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static String toJsonStr(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.info("data deserialize error.", e);
        }
        return "{}";
    }
}
