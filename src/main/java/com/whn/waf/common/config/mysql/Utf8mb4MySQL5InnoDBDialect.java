package com.whn.waf.common.config.mysql;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @author Administrator.
 * @since 0.1 created on 2017/3/19 0019.
 */
public class Utf8mb4MySQL5InnoDBDialect extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}
