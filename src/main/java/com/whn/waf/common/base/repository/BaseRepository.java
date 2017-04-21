package com.whn.waf.common.base.repository;

import com.whn.waf.common.base.domain.BaseDomain;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;


/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */

@NoRepositoryBean
public interface BaseRepository<T extends BaseDomain<I>, I extends Serializable> extends PagingAndSortingRepository<T, I>{
    List<T> findAll();
}
