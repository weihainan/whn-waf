package com.whn.waf.common.config.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import java.util.Properties;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/11.
 */
@EnableMongoAuditing
@EnableAspectJAutoProxy(proxyTargetClass = true)
public abstract class AbstractMongoConfigurerAdapter extends AbstractMongoConfiguration {

    public static final String MONGO_URL_PROPERTY = "url";
    public static final String MONGO_DB_NAME_PROPERTY = "dbName";
    public static final String MONGO_DB_MAPPING_BASE_PACKAGE = "mappingBasePackage";

    private String connectionString;
    private String dbName;
    private String mappingBasePackage;

    @Autowired
    private ApplicationContext appContext;

    public AbstractMongoConfigurerAdapter()  {
        try {
            Properties properties = PropertiesLoaderUtils
                    .loadProperties(new ClassPathResource(configFileNamePrefix() + ".properties"));

            checkProperties(properties);

            connectionString = (String) properties.get(MONGO_URL_PROPERTY);
            dbName = (String) properties.get(MONGO_DB_NAME_PROPERTY);
            mappingBasePackage = (String) properties.get(MONGO_DB_MAPPING_BASE_PACKAGE);

        } catch (Exception e) {
            System.out.println(e);
            throw  WafBizException.of(ErrorCode.CONFIG_LOADING_FAIL, e.getMessage());
        }
    }

    private void checkProperties(Properties properties) throws Exception {
        if (!properties.containsKey(MONGO_URL_PROPERTY)) {
            throw  WafBizException.of(ErrorCode.CONFIG_LOADING_FAIL, MONGO_URL_PROPERTY + " Not Found");
        }
        if (!properties.containsKey(MONGO_DB_NAME_PROPERTY)) {
            throw  WafBizException.of(ErrorCode.CONFIG_LOADING_FAIL, MONGO_DB_NAME_PROPERTY + " Not Found");
        }
        if (!properties.containsKey(MONGO_DB_MAPPING_BASE_PACKAGE)) {
            throw  WafBizException.of(ErrorCode.CONFIG_LOADING_FAIL, MONGO_DB_MAPPING_BASE_PACKAGE + " Not Found");
        }
    }

    //配置文件名
    protected String configFileNamePrefix() {
        return "mdb";
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    protected String getMappingBasePackage() {
        return mappingBasePackage;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        MongoClientURI mongoClientURI = new MongoClientURI(connectionString);
        return new MongoClient(mongoClientURI);
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoDbFactory factory = mongoDbFactory();

        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setApplicationContext(appContext);

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(factory, converter);
    }

    @Bean
    public MongoRepositoryFactory mongoRepositoryFactory(MongoOperations mongoOperations) {
        return new MongoRepositoryFactory(mongoOperations);
    }
}
