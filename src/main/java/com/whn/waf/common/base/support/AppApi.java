package com.whn.waf.common.base.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解析api path variable中application中的值
 * 如果放在类上，则所有方法都检查，没有就报异常
 * 如果放方法上，则只检查对应方法，没有就报异常
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AppApi {

}