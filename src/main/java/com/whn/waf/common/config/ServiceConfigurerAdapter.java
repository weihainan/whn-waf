package com.whn.waf.common.config;


import com.whn.waf.common.client.http.WafHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */

@Configuration
@EnableScheduling   // 可以使用定时任务 @Scheduled
@ComponentScan(basePackages = "com.whn",
        excludeFilters = @ComponentScan.Filter({Controller.class, Configuration.class,
                Repository.class, ControllerAdvice.class}))
public class ServiceConfigurerAdapter {

    @Bean
    public WafHttpClient wafHttpClient() {
        return new WafHttpClient();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        return executor;
    }

}

