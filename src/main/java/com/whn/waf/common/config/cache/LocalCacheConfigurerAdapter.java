package com.whn.waf.common.config.cache;

import java.util.concurrent.TimeUnit;

import com.whn.waf.common.config.cache.condition.LocalCacheSupportCondition;
import com.whn.waf.common.context.WafProperties;
import com.whn.waf.common.exception.WafBizException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

/**
 * 本地缓存配置基础类
 * Created by Zhang Jinlong(150429) on 2016/4/12.
 */
@Conditional(LocalCacheSupportCondition.class)   // 放在@Bean @Configuration注解的类上，当条件满足是 该类初始化
@Configuration
public class LocalCacheConfigurerAdapter extends AbstractCacheConfigurerAdapter {

    public LocalCacheConfigurerAdapter() {
        if (WafProperties.isRedisCacheSupport()) {
            throw WafBizException.of("when redis cache is available,your cache config must extends RedisCacheConfigurerAdapter");
        }
    }

    /**
     * 通过缓存存储的类型、缓存名、过期时间(单位秒)创建一个CacheManager
     *
     * @param type       缓存存储的类型
     * @param cacheName  缓存名
     * @param expireTime 过期时间(单位秒)
     * @return CacheManager
     */
    @Override
    protected CacheManager newCacheManager(Class type, String cacheName, long expireTime) {
        return newCacheManager(cacheName, expireTime);
    }

    /**
     * 通过缓存存储的类型、缓存名、过期时间(单位秒)创建一个存放集合的CacheManager
     *
     * @param type       缓存存储的类型（即集合的泛型）
     * @param cacheName  缓存名
     * @param expireTime 过期时间(单位秒)
     * @return CacheManager
     */
    @Override
    protected CacheManager newCollectionCacheManager(Class type, String cacheName, long expireTime) {
        return newCacheManager(cacheName, expireTime);
    }

    public static GuavaCacheManager newCacheManager(String cacheName, long expireTime) {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager(cacheName);
        guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder().expireAfterWrite(expireTime, TimeUnit.SECONDS));
        return guavaCacheManager;
    }
}

