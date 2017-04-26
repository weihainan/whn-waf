package com.whn.waf.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
@Component
public class Context implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private Context() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initContextHolder(applicationContext);
    }

    private static void initContextHolder(ApplicationContext context) {
        Context.applicationContext = context;
    }

    public static ApplicationContext getApplicatinContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

}
