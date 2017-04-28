package com.whn.waf.common.config.mysql.impl;

import com.whn.waf.common.base.domain.BaseDomain;
import com.whn.waf.common.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author Administrator.
 * @since 0.1 created on 2017/3/21 0021.
 */
public class BaseRepositoryImpl<T extends BaseDomain<I>, I extends Serializable> extends SimpleJpaRepository<T, I>
        implements BaseRepository<T, I> {

    private final EntityManager em;
    private final Class<T> domainType;

    public BaseRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.domainType = entityInformation.getJavaType();
    }
}
