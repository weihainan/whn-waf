package com.whn.waf.common.config;

import com.whn.waf.common.base.support.ApplicationResolveInterceptor;
import com.whn.waf.common.support.WafJsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/11.
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport           // 可以使用Pageable作为controller的参数  page size sort=firstname&sort=lastname,asc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.whn", includeFilters = @ComponentScan.Filter({Controller.class}))
public abstract class AbstractWebMvcConfigurerAdpter extends WebMvcConfigurerAdapter {

    private static final boolean jaxb2Present =
            ClassUtils.isPresent("javax.xml.bind.Binder", AbstractWebMvcConfigurerAdpter.class.getClassLoader());

    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", AbstractWebMvcConfigurerAdpter.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", AbstractWebMvcConfigurerAdpter.class.getClassLoader());


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).favorParameter(false);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //argumentResolvers.add(new ListParamHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 默认非 UTF-8
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        stringConverter.setWriteAcceptCharset(false);

        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(stringConverter);
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<Source>());
        converters.add(new AllEncompassingFormHttpMessageConverter());

        if (jaxb2Present) {
            converters.add(new Jaxb2RootElementHttpMessageConverter());
        }
        if (jackson2Present) {
            MappingJackson2HttpMessageConverter convert = new MappingJackson2HttpMessageConverter();
            convert.setObjectMapper(WafJsonMapper.getMapper());

            //重置媒体类型不带charset
            List<MediaType> supportedMediaTypes = new ArrayList<>();
            supportedMediaTypes.add(MediaType.APPLICATION_JSON);
            convert.setSupportedMediaTypes(supportedMediaTypes);

            converters.add(convert);
        }
        customMediaTypeSupport(converters);
    }

    public void customMediaTypeSupport(List<HttpMessageConverter<?>> converters) {
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(5 * 1024 * 1024);
        resolver.setMaxInMemorySize(512 * 1024);
        return resolver;
    }

    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new HandlerExceptionResolverComposite();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new ApplicationResolveInterceptor());
    }

}

