package com.whn.waf.common.config.cache.condition;

import com.whn.waf.common.context.WafProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 本地缓存组件注册条件
 * Created by WeiHaiNan on 2016/10/10.
 */
public class LocalCacheSupportCondition implements Condition{

	@Override
	public boolean matches(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		return !WafProperties.isRedisCacheSupport();
	}

}
