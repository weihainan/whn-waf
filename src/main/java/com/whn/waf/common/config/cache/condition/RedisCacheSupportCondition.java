package com.whn.waf.common.config.cache.condition;

/**
 * Redis缓存组件注册条件
 *
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */

import com.whn.waf.common.context.WafApplicationContext;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RedisCacheSupportCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context,
                           AnnotatedTypeMetadata metadata) {
        return WafApplicationContext.isRedisCacheSupport();
    }

}