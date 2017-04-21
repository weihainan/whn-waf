package com.whn.waf.common.config.mongo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/11.
 */
@EnableMongoRepositories(
        basePackages = "com.whn",
        includeFilters = @ComponentScan.Filter(Repository.class)
)
public class MongoConfigurerAdapter extends AbstractMongoConfigurerAdapter {

}