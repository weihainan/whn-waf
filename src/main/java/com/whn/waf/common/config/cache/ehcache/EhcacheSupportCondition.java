package com.whn.waf.common.config.cache.ehcache;

import com.whn.waf.common.context.WafProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/4/26.
 */
public class EhcacheSupportCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context,
                           AnnotatedTypeMetadata metadata) {
        return WafProperties.isEhcacheCacheSupport();
    }

}