package com.whn.waf.common.config.cache;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.whn.waf.common.config.cache.condition.RedisCacheSupportCondition;
import com.whn.waf.common.context.WafApplicationContext;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.support.WafJsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Redis缓存配置基础类
 * Created by Zhang Jinlong(150429) on 2016/4/12.
 */
@Conditional(RedisCacheSupportCondition.class) // 放在@Bean @Configuration注解的类上，当条件满足是 该类初始化
@Configuration
@PropertySource(value = "classpath:redis.properties")
public class RedisCacheConfigurerAdapter extends AbstractCacheConfigurerAdapter {

    private final ObjectMapper objectMapper;

    @Value("${redis.host:\'127.0.0.1\'}")
    private String host = "127.0.0.1";

    @Value("${redis.port:6379}")
    private int port = 6379;

    @Value("${redis.db:0}")
    private int db = 0;

    @Value("${redis.pool.maxActive:#{T(redis.clients.jedis.JedisPoolConfig).DEFAULT_MAX_TOTAL}}")
    private int maxActive;

    @Value("${redis.pool.maxIdle:#{T(redis.clients.jedis.JedisPoolConfig).DEFAULT_MAX_IDLE}}")
    private int maxIdle;

    @Value("${redis.pool.minIdle:#{T(redis.clients.jedis.JedisPoolConfig).DEFAULT_MIN_IDLE}}")
    private int minIdle;

    @Value("${redis.pool.maxWait:#{T(redis.clients.jedis.JedisPoolConfig).DEFAULT_MAX_WAIT_MILLIS}}")
    private long maxWait;

    @Value("${redis.pool.testOnBorrow:#{T(redis.clients.jedis.JedisPoolConfig).DEFAULT_TEST_ON_BORROW}}")
    private boolean testOnBorrow;

    public RedisCacheConfigurerAdapter() {
        if (!WafApplicationContext.isRedisCacheSupport()) {
            throw WafBizException.of("when redis cache is unavailable,your cache config must extends LocalCacheConfigurerAdapter");
        }
        objectMapper = objectMapper();
    }

    public void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

    protected ObjectMapper objectMapper() {
        return WafJsonMapper.getMapper();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(db);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate idgRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisScript<String> idgScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("idg.lua")));
        redisScript.setResultType(String.class);
        return redisScript;
    }

    /**
     * 通过缓存存储的类型、缓存名、过期时间(单位秒)创建一个CacheManager
     *
     * @param type       缓存存储的类型
     * @param cacheName  缓存名
     * @param expireTime 过期时间(单位秒)
     * @return CacheManager
     */
    protected CacheManager newCacheManager(Class type, String cacheName, long expireTime) {
        return getCacheManager(cacheName, new Jackson2JsonRedisSerializer(type), expireTime);
    }

    /**
     * 通过缓存存储的类型、缓存名、过期时间(单位秒)创建一个存放集合的CacheManager
     *
     * @param type       缓存存储的类型（即集合的泛型）
     * @param cacheName  缓存名
     * @param expireTime 过期时间(单位秒)
     * @return CacheManager
     */
    protected CacheManager newCollectionCacheManager(Class type, String cacheName, long expireTime) {
        CollectionType collectionType =
                objectMapper.getTypeFactory().constructCollectionType(List.class, type);
        return getCacheManager(cacheName, new Jackson2JsonRedisSerializer(collectionType), expireTime);
    }

    private CacheManager getCacheManager(String cacheName, Jackson2JsonRedisSerializer redisSerializer, long expireTime) {
        redisSerializer.setObjectMapper(objectMapper);
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setDefaultSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(cacheName);
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate, cacheNames);
        cacheManager.setUsePrefix(true);
        //12 hours
        cacheManager.setDefaultExpiration(expireTime);
        //事务提交后进行缓存操作
        cacheManager.setTransactionAware(true);
        cacheManager.afterPropertiesSet();
        return cacheManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

