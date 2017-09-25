package com.whn.waf.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/25
 */
public class TestUtils {

    /**
     * 填充一个对象（一般用于测试）
     */
    public static <T> T fullObject(Class<T> cl) {
        T t = null;
        try {
            t = cl.newInstance();
            Method methods[] = cl.getMethods();
            for (Method method : methods) {
                // 如果是set方法,进行随机数据的填充
                if (method.getName().indexOf("set") == 0) {
                    Class param = method.getParameterTypes()[0];
                    if (param.equals(String.class)) {
                        method.invoke(t, RandomUtil.getRandomString(5));
                    } else if (param.equals(Short.class)) {
                        method.invoke(t, (short) new Random().nextInt(5));
                    } else if (param.equals(Float.class)) {
                        method.invoke(t, new Random().nextFloat());
                    } else if (param.equals(Double.class)) {
                        method.invoke(t, new Random().nextDouble());
                    } else if (param.equals(Integer.class)) {
                        method.invoke(t, new Random().nextInt(10));
                    } else if (param.equals(Long.class)) {
                        method.invoke(t, new Random().nextLong());
                    } else if (param.equals(Date.class)) {
                        method.invoke(t, new Date());
                    } else if (param.equals(Timestamp.class)) {
                        method.invoke(t, new Timestamp(System.currentTimeMillis()));
                    } else if (param.equals(java.sql.Date.class)) {
                        method.invoke(t, new java.sql.Date(System.currentTimeMillis()));
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }
}
