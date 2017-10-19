package com.whn.waf.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.whn.waf.common.support.WafJsonMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/4/20.
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * String id = DigestUtils.md5Hex(String.format("%s_%s_%s_%s", application, tenant, ownerId, auditorId))
     */
    public static String md5Hex(String formatStr, Object... objects) {
        return DigestUtils.md5Hex(String.format(formatStr, objects));
    }

    public static String sha256Hex(String formatStr, Object... objects) {
        return DigestUtils.sha256Hex(String.format(formatStr, objects));
    }

    /**
     * 把map先转为json,再转为实体
     */
    public static <T> T getData(Object object, Class<T> type) {
        if (object == null || null ==type) {
            return null;
        }
        try {
            return WafJsonMapper.parse(WafJsonMapper.toJson(object), type);
        } catch (Exception e) {
            logger.info("data deserialize error.", e);
        }
        return null;
    }

    public static String toJson(Object object) {
        try {
            return WafJsonMapper.toJson(object);
        } catch (IOException e) {
            logger.info("data deserialize error.", e);
        }
        return "{}";
    }

    /**
     * 转为map 并且属性名字改为下划线
     *
     * @param object
     * @return
     */
    public static Map toMap(Object object) {
        try {// 转驼峰为下划线
            return WafJsonMapper.parse(WafJsonMapper.toJson(object), HashMap.class);
        } catch (Exception e) {
            logger.info("data deserialize error.", e);
        }
        return Maps.newHashMap();
    }

    public static <T> List<T> arrayToList(T[] arr) {
        if (arr == null || arr.length == 0) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(arr);
    }

    public static <T> List<T> removeNull(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        List<T> news = Lists.newArrayList();
        for (T t : list) {
            if (t != null) {
                news.add(t);
            }
        }
        return news;
    }

    public static <T> Set<T> arrayToSet(T[] arr) {
        if (arr == null || arr.length == 0) {
            return Sets.newHashSet();
        }
        return Sets.newHashSet(arr);
    }

    public static String[] listToArr(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new String[0];
        }
        return list.toArray(new String[list.size()]);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid32() {
        return uuid().replace("-", "").toUpperCase();
    }

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

    public static String trim(Object object) {
        String timeStr = String.valueOf(object).trim();
        if (StringUtils.isBlank(timeStr) || "null".equals(timeStr)) {
            return "";
        }
        return timeStr;
    }

    public static String trim(Object object, String defaultValueIfNull) {
        String timeStr = String.valueOf(object).trim();
        if (StringUtils.isBlank(timeStr) || "null".equals(timeStr)) {
            return defaultValueIfNull;
        }
        return timeStr;
    }

    public static boolean isBlank(String string) {
        return null == string || "".equals(string.trim());
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static String checkNullAndConvert(String str) {
        return isBlank(str) ? "" : str;
    }

    /**
     * 获得字符串的描述信息 前30个字符
     */
    public static String getDescString(String str) {
        return getDescString(str, 30);
    }

    /**
     * 获得字符串的描述信息 前n个字符 默认10
     */
    public static String getDescString(String str, int length) {
        if (length <= 0) {
            length = 10;
        }
        if (isNotBlank(str) && str.trim().length() > length) {
            return str.substring(0, length);
        }
        return str;
    }

    public static boolean isEmpty(Collection collection) {
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

    public static String makeQueryStringAllRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*").replace("+", "\\+").replace("|", "\\|").replace("{", "\\{").replace("}", "\\}").replace("(", "\\(").replace(")", "\\)").replace("^", "\\^").replace("$", "\\$").replace("[", "\\[").replace("]", "\\]").replace("?", "\\?").replace(",", "\\,").replace(".", "\\.");
    }
}
