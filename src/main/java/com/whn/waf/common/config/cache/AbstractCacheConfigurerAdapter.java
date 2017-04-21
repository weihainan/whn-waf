package com.whn.waf.common.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 通用缓存配置基础类
 * Created by Zhang Jinlong(150429) on 2016/4/12.
 */
@EnableCaching(proxyTargetClass = true)
public abstract class AbstractCacheConfigurerAdapter {

    public static final long DEFAULT_EXPIRE_TIME = 60L * 60 * 12;


    @Bean
    public CacheManager cacheManager() {
        Set<CacheManager> cacheManagers = new HashSet<>();
        cacheManagers.addAll(customCacheManagers());

        CompositeCacheManager cacheManager = new CompositeCacheManager();
        cacheManager.setCacheManagers(cacheManagers);
        cacheManager.afterPropertiesSet();
        return cacheManager;
    }

    /**
     * 用户自定义的缓存，可以通过newCacheManager及newCollectionCacheManager创建CacheManager
     *
     * @return 用户自定义的缓存
     */
    protected Set<CacheManager> customCacheManagers() {
        return Collections.emptySet();
    }

    /**
     * 通过缓存存储的类型、缓存名、过期时间(单位秒)创建一个CacheManager
     *
     * @param type       缓存存储的类型
     * @param cacheName  缓存名
     * @param expireTime 过期时间(单位秒)
     * @return CacheManager
     */
    protected abstract CacheManager newCacheManager(Class type, String cacheName, long expireTime);

    /**
     * 通过缓存存储的类型、缓存名创建一个CacheManager，过期时间默认为{@code DEFAULT_EXPIRE_TIME}
     *
     * @param type      缓存存储的类型
     * @param cacheName 缓存名
     * @return CacheManager
     */
    protected CacheManager newCacheManager(Class type, String cacheName) {
        return newCacheManager(type, cacheName, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 通过缓存存储的类型、缓存名、过期时间(单位秒)创建一个存放集合的CacheManager
     *
     * @param type       缓存存储的类型（即集合的泛型）
     * @param cacheName  缓存名
     * @param expireTime 过期时间(单位秒)
     * @return CacheManager
     */
    protected abstract CacheManager newCollectionCacheManager(Class type, String cacheName, long expireTime);

    /**
     * 通过缓存存储的类型、缓存名创建一个存放集合的CacheManager，过期时间默认为{@code DEFAULT_EXPIRE_TIME}
     *
     * @param type      缓存存储的类型（即集合的泛型）
     * @param cacheName 缓存名
     * @return CacheManager
     */
    protected CacheManager newCollectionCacheManager(Class type, String cacheName) {
        return newCollectionCacheManager(type, cacheName, DEFAULT_EXPIRE_TIME);
    }
}
