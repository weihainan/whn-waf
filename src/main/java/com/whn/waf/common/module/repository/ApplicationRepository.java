package com.whn.waf.common.module.repository;

import com.whn.waf.common.base.repository.BaseRepository;
import com.whn.waf.common.module.domain.Application;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/5/26.
 */
@Repository
public interface ApplicationRepository extends BaseRepository<Application, String> {
    Application findByName(String name);
    List<Application> findByIdIn(Set<String> ids);
}
