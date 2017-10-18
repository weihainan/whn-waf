package com.whn.waf.common.utils;

import com.whn.waf.common.support.ParamBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在使用Aspect时，通过切点JoinPoint获取方法的的相关信息
 *
 * @author weihainan.
 * @since 0.1 created on 2017/2/23.
 */
public final class JoinPointUtil {

    /**
     * 获取被执行方法的参数信息，包含名称、类型和值
     *
     * @param joinPoint 切点
     * @return 方法参数信息列表，每一个参数为一个map，包含name、type、value
     */
    public static List<Map<String, Object>> getMethodParamNameAndValue(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        List<Map<String, Object>> params = new ArrayList<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.add(ParamBuilder.of(parameterNames[i], parameterValues[i]).build());
        }
        return params;
    }

    /**
     * 获取被执行方法的参数信息，包含名称、类型和值
     *
     * @param joinPoint 切点
     * @return 方法参数信息列表，每一个参数为一个map，包含name、type、value
     */
    public static List<Param> getMethodParameters(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Object[] parameterValues = joinPoint.getArgs();
        List<Param> params = new ArrayList<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.add(Param.of(parameterNames[i], parameterTypes[i], parameterValues[i]));
        }
        return params;
    }

    public static class Param {
        private String name;
        @Transient
        private Class type;
        private Object value;

        public static Param of(String name, Class type, Object value) {
            Param param = new Param();
            param.setName(name);
            param.setType(type);
            param.setValue(value);
            return param;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    /**
     * 获取请求的类型，即RequestMapping的method
     *
     * @param joinPoint 切点
     * @return 请求的类型
     */
    public static RequestMethod getRequestMethod(JoinPoint joinPoint) {
        RequestMethod[] requestMethods = ((MethodSignature) joinPoint.getSignature()).getMethod()
                .getAnnotation(RequestMapping.class).method();
        if (requestMethods.length == 0) {
            return RequestMethod.GET;
        }
        return requestMethods[0];
    }

    /**
     * 获取请求映射的路径，即RequestMapping的value
     *
     * @param joinPoint 切点
     * @return 请求映射的路径
     */
    private static String getRequestMappingValue(JoinPoint joinPoint) {
        String[] values = ((MethodSignature) joinPoint.getSignature()).getMethod()
                .getAnnotation(RequestMapping.class).value();
        return values.length > 0 ? values[0] : "";
    }

    /**
     * 获取请求的url 类和方法上的RequestMapping的value的拼接
     *
     * @param joinPoint 切点
     * @return 获取请求的url
     */
    public static String getRequestUrl(JoinPoint joinPoint) {
        try {
            RequestMapping requestMapping = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
            return requestMapping.value()[0].trim() + getRequestMappingValue(joinPoint).trim();
        } catch (NullPointerException e) {
            return getRequestMappingValue(joinPoint);
        }
    }

    /**
     * 获取请求方法类的名称
     *
     * @param joinPoint 切点
     * @return 求方法类的名称
     */
    public static String getClassName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName();
    }

    /**
     * 获取请求方法的名
     *
     * @param joinPoint 切点
     * @return 请求方法的名
     */
    public static String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    /**
     * 获取请求的方法的Method对象
     *
     * @param point 切点
     * @return 请求的方法的Method对象
     * @throws NoSuchMethodException
     */
    public static Method getMethod(JoinPoint point) throws NoSuchMethodException {
        MethodSignature msig = (MethodSignature) point.getSignature();
        return point.getTarget().getClass().
                getDeclaredMethod(msig.getName(), msig.getParameterTypes());
    }

    /**
     * 获取方法所在类的Class对象
     *
     * @param point 切点
     * @return 方法所在类的Class对象
     * @throws NoSuchMethodException
     */
    public static Class getClass(JoinPoint point) throws NoSuchMethodException {
        return point.getTarget().getClass();
    }

    /**
     * 获取被执行方法上的注解
     *
     * @param point 切点
     * @param type  注解类型
     * @param <T>   泛型参数
     * @return 被执行方法上的注解
     * @throws NoSuchMethodException
     */
    public static <T extends Annotation> T getMethodAnnotation(JoinPoint point, Class<T> type) throws NoSuchMethodException {
        return getMethod(point).getAnnotation(type);
    }

    /**
     * 获取方法上的注解
     *
     * @param method 方法
     * @param type   注解类型
     * @param <T>    泛型参数
     * @return 方法上的注解
     */
    public static <T extends Annotation> T getAnnotation(Method method, Class<T> type) {
        return method.getAnnotation(type);
    }

}
