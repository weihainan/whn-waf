package com.whn.waf.common.config;

import com.whn.waf.common.filter.ExceptionFilter;
import com.whn.waf.common.filter.LogMDCFilter;
import com.whn.waf.common.filter.WafCORSFilter;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/11.
 */
@Order(1)
public abstract class AbstractWebApplicationInitialzer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Specify the servlet mapping(s) for the DispatcherServlet
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        initFilters(servletContext);
        registerFilters(servletContext);
    }

    /**
     * 设置設置当spring没有找到Handler的时候，抛出NoHandlerFountException异常。并且被异常捕获到。统一进行处理
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

    private void initFilters(ServletContext servletContext) {

        //DelegatingFilterProxy 主要作用就是一个代理模式的应用,可以把servlet 容器中的filter同spring容器中的bean关联起来
        //注意一个DelegatingFilterProxy的一个初始化参数：targetFilterLifecycle ,其默认值为false 。
        //但如果被其代理的filter的init()方法和destry()方法需要被调用时，需要设置targetFilterLifecycle为true。
        //addFilter(servletContext, "exceptionFilter", new DelegatingFilterProxy("exceptionFilter"));

        // 设置编码拦截器
        initCharacterEncodingFilter(servletContext);
        initWafCROSFilter(servletContext);
        initLogMDCFilter(servletContext);
        initExceptionFilter(servletContext);
    }

    /**
     * 由子类复写以增加新的拦截器
     */
    protected void registerFilters(ServletContext servletContext) {

    }

    /**
     * 字符编码过滤器 可以由子类复写以改变行为
     */
    protected void initCharacterEncodingFilter(ServletContext servletContext) {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        // characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        addFilter(servletContext, "characterEncodingFilter", characterEncodingFilter);
    }

    /**
     * 跨域处理LogMDCFilter
     */
    protected void initWafCROSFilter(ServletContext servletContext) {
        WafCORSFilter wafCORSFilter = new WafCORSFilter();
        addFilter(servletContext, "wafCORSFilter", wafCORSFilter);
    }

    /**
     * log4j中获取特殊信息，如userId，根据需要修改LogMDCFilter
     */
    protected void initLogMDCFilter(ServletContext servletContext) {
        LogMDCFilter logMDCFilter = new LogMDCFilter();
        addFilter(servletContext, "logMDCFilter", logMDCFilter);
    }

    protected void initExceptionFilter(ServletContext servletContext) {
        ExceptionFilter exceptionFilter = new ExceptionFilter();
        addFilter(servletContext, "exceptionFilter", exceptionFilter);
    }

    /**
     * 添加一个拦截器 可以在registerFilters使用
     */
    protected void addFilter(ServletContext servletContext, String filterName, Filter filter) {
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(filterName, filter);
        filterRegistration.setAsyncSupported(isAsyncSupported());
        filterRegistration.addMappingForUrlPatterns(getDispatcherTypes(), false, "/*");
    }

    protected EnumSet<DispatcherType> getDispatcherTypes() {
        return isAsyncSupported()
                ? EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
                DispatcherType.ASYNC)
                : EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
    }

}

