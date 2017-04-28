package com.whn.waf.common.config.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.whn.waf.common.config.mysql.impl.BaseRepositoryImpl;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.ValidationMode;
import javax.sql.DataSource;

/**
 *
 */
@Conditional(MySqlSupportCondition.class)
@Configuration
@PropertySource(value = {"classpath:dbconfig.properties"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.whn",
        repositoryBaseClass = BaseRepositoryImpl.class,
        enableDefaultTransactions = false, // 关闭默认事务 使用Transaction显式声明事务
        includeFilters = @ComponentScan.Filter(Repository.class))
public class MySQLConfig {

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.username}")
    private String user;

    @Value("${mysql.password}")
    private String password;

    @Value("${mysql.driver_class}")
    private String driverName;

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setJdbcUrl(url);
        dataSource.setPassword(password);
        setDataSourceProperty(dataSource);
        return dataSource;
    }

    private void setDataSourceProperty(ComboPooledDataSource dataSource) {
        dataSource.setInitialPoolSize(15);
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxIdleTime(25200);
        dataSource.setMaxStatements(0);
        dataSource.setIdleConnectionTestPeriod(30);
        dataSource.setAcquireIncrement(20);
        dataSource.setAcquireRetryAttempts(30);
        dataSource.setPreferredTestQuery("SELECT 1");
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setBreakAfterAcquireFailure(true);
    }


    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(mysqlDataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(mysqlDataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(generateDdl());
        vendorAdapter.setShowSql(showSql());

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getMappingBasePackage());
        factory.setDataSource(mysqlDataSource());
        factory.getJpaPropertyMap().put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        // factory.getJpaPropertyMap().put("hibernate.ejb.interceptor", TableMapperInterceptor.class.getCanonicalName());
        factory.getJpaPropertyMap().put(Environment.DIALECT, dialect().getClass().getCanonicalName());
        factory.setValidationMode(ValidationMode.NONE);
        factory.afterPropertiesSet();
        return factory;
    }

    protected boolean generateDdl() {
        return true;
    }

    protected boolean showSql() {
        //默认仅开发环境打印sql
        return true;
    }

    protected String getMappingBasePackage() {
        return "com.whn";
    }

    @Bean
    public Dialect dialect() {
        return new Utf8mb4MySQL5InnoDBDialect();
    }

}
