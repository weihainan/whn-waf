package com.whn.waf.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.support.WafJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean和Map互相转换工具类
 */
public class BeanMapUtil {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BeanMapUtil() {
    }

    /**
     * 将Map对象转换成Bean对象
     *
     * @param map 待转换的Map对象
     * @param obj 转换后的Bean对象
     */
    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void convertMapToBean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            logger.error("transMap2Bean Error " + e);
        }
        return;

    }

    /**
     * 将Bean对象转换成Map对象
     *
     * @param bean 待转的Bean对象
     * @return 转换后的Map对象
     */
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> convertBeanToMap(Object bean) {

        if (bean == null) {
            return null;
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    returnMap.put(key, value);
                }
            }

        } catch (Exception e) {
            logger.error("convertBeanToMap Error " + e);
        }
        return returnMap;
    }


    private static ObjectMapper objectMapper = WafJsonMapper.getMapper();

    /**
     * 将oldDomain与map里面的值合并复制到新的domain对象中去<br/>
     * map里的值将会覆盖oldDomain里的同名属性<br/>
     * 如果只是从map生成一个对象,old是新new出来的对象。
     */
    @SuppressWarnings("unchecked")
    public static <T> T createDomainFromOldAndMap(T oldDomain,
                                                  Map<String, Object> map) {
        try {
            T newDomain = (T) oldDomain.getClass().newInstance();
            BeanUtils.copyProperties(oldDomain, newDomain);
            String json = objectMapper.writeValueAsString(map);
            return objectMapper.readerForUpdating(newDomain).readValue(json);
        } catch (IllegalArgumentException | IOException e) {
            throw WafBizException.of(ErrorCode.INVALID_ARGUMENT);
        } catch (InstantiationException | IllegalAccessException e) {
            String message = "类[" + oldDomain.getClass().getCanonicalName() + "]找不到可访问无参构造器";
            throw WafBizException.of(ErrorCode.FAIL, message);
        }
    }

    public static <T> T createDomainFromMap(Class<T> type,
                                            Map<String, Object> map) {
        try {
            T newDomain = type.newInstance();
            String json = objectMapper.writeValueAsString(map);
            return objectMapper.readerForUpdating(newDomain).readValue(json);
        } catch (IllegalArgumentException | IOException e) {
            throw WafBizException.of(ErrorCode.INVALID_ARGUMENT);
        } catch (InstantiationException | IllegalAccessException e) {
            String message = "类[" + type.getCanonicalName() + "]找不到可访问无参构造器";
            throw WafBizException.of(ErrorCode.FAIL, message);
        }
    }
}
