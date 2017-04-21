package com.whn.waf.common.base.repository;

import com.whn.waf.common.base.domain.BizDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */

@NoRepositoryBean
public interface BizRepository<T extends BizDomain<I>, I extends Serializable> extends BaseRepository<T, I> {

    T findByIdAndDeletedIsFalse(I id);

    List<T> findByDeletedIsFalse();

    Page<T> findByDeletedIsFalse(Pageable pageable);

    List<T> findByDeletedIsTrue();
}
