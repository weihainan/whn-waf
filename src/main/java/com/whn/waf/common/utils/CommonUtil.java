package com.whn.waf.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/4/20.
 */
public class CommonUtil {

    public static String dealStrIfNull(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    public static long dealLongIfNull(Object object) {
        String timeStr = String.valueOf(object).trim();
        if (StringUtils.isBlank(timeStr) || "null".equals(timeStr)) {
            return 0L;
        }
        return Double.valueOf(timeStr).longValue();
    }

    public static boolean isBlank(String string) {
        return null == string || "".equals(string.trim());
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public  static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }


    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static void main(String[] args) {
        System.out.println(isBlank(""));
        System.out.println(isBlank(null));
        System.out.println(isBlank("  "));
    }
}
