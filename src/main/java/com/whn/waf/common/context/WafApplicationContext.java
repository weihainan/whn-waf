package com.whn.waf.common.context;

/**
 * 系统环境上下文 获取项目配置项
 *
 * @since 0.1 created on 2017/3/10.
 */

import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class WafApplicationContext {

    // 系统全局配置文件名称 放在resource文件下
    private static final String WAF_PROPERTIES_FILE_NAME = "waf.properties";

    private static final String PROJECT_NAME = "project.name";
    private static final String DEBUG_MODE = "debug";
    private static final String ERROR_CODE_PREFIX = "error.code.prefix";

    private static Properties wafProperties;

    private static boolean redisCacheSupport; // 是否是redis支持的缓存

    static {
        try {
            redisCacheSupport = existsPropertiesFile("redis.properties");
            wafProperties = PropertiesLoaderUtils
                    .loadProperties(new ClassPathResource(
                            WAF_PROPERTIES_FILE_NAME));
            checkProperties(wafProperties);
        } catch (FileNotFoundException e) {
            throw WafBizException.of(ErrorCode.CONFIG_MISSING, "配置文件未找到："
                    + WAF_PROPERTIES_FILE_NAME);
        } catch (IOException e) {
            throw WafBizException.of(ErrorCode.CONFIG_LOADING_FAIL, "配置文件加载失败："
                    + WAF_PROPERTIES_FILE_NAME);
        }
    }

    private static void checkProperties(Properties properties) {
        if (!properties.containsKey(PROJECT_NAME)) {
            throw WafBizException.of(ErrorCode.CONFIG_MISSING_ITEM, "配置项未找到："
                    + PROJECT_NAME);
        }

        if (!properties.containsKey(ERROR_CODE_PREFIX)) {
            throw WafBizException.of(ErrorCode.CONFIG_MISSING_ITEM, "配置项未找到："
                    + ERROR_CODE_PREFIX);
        }

        if (!properties.containsKey(DEBUG_MODE)) {
            throw WafBizException.of(ErrorCode.CONFIG_MISSING_ITEM, "配置项未找到："
                    + DEBUG_MODE);
        }
    }

    private static boolean existsPropertiesFile(String propertiesFile) {
        try {
            PropertiesLoaderUtils.loadProperties(new ClassPathResource(propertiesFile));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw WafBizException.of(ErrorCode.CONFIG_LOADING_FAIL, propertiesFile);
        }
    }

    public static String getProjectName() {
        return wafProperties.getProperty(PROJECT_NAME);
    }

    public static boolean isDebugMode() {
        return Boolean.valueOf(wafProperties.getProperty(DEBUG_MODE));
    }

    public static String getErrorCodePrefix() {
        return wafProperties.getProperty(ERROR_CODE_PREFIX);
    }

    public static boolean isRedisCacheSupport() {
        return redisCacheSupport;
    }

    public static Properties getProperties() {
        return wafProperties;
    }

    public static String getProperty(String key) {
        return wafProperties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return wafProperties.getProperty(key, defaultValue);
    }

    public static void setProperty(String key, String value) {
        wafProperties.setProperty(key, value);
    }


    public static int getPropertyForInteger(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("转换 \"" + value + "\" 为 int 过程发生错误，引发的 properties 属性为 " + key);
        }
    }

    public static int getPropertyForInteger(String key, String defaultValue) {
        String value = getProperty(key, defaultValue);
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("转换 \"" + value + "\" 为 int 过程发生错误，引发的 properties 属性为 " + key);
        }
    }

    public static boolean getPropertyForBoolean(String key, String defaultValue) {
        String value = getProperty(key, defaultValue);
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("转换 \"" + value + "\" 为 boolean 过程发生错误，引发的 properties 属性为 " + key);
        }
    }

}