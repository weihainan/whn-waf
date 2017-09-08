package com.whn.waf.common.base.service;

import com.whn.waf.common.base.domain.BasalModuleDomain;
import com.whn.waf.common.base.repository.BasalModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/8
 */
public abstract class BasalModuleService<T extends BasalModuleDomain<I>, I extends Serializable> extends BaseService<T, I> {

    @Autowired
    private BasalModuleRepository<T, I> basalModuleRepository;

    @Override
    public T findOne(I id) {
        T t = super.findOne(id);
        if (t == null || t.isDeleted()) {
            return null;
        }
        return t;
    }

    @Override
    public List<T> findAll() {
        return basalModuleRepository.findByDeletedIsFalse();
    }

    @Override
    public Page<T> paging(Pageable pageable) {
        return basalModuleRepository.findByDeletedIsFalse(pageable);
    }

    // 添加或内部更新
    @Override
    public T add(T t) {
        t.setDeleted(false);
        return super.add(t);
    }

    /**
     * 软删除
     */
    @Override
    public void delete(T t) {
        if (t == null || t.isDeleted()) {
            throw module().notFound();
        }
        checkId(t.getId());
        beforeDelete(t);
        t.setDeleted(true);
        basalModuleRepository.save(t);
    }

    @Override
    public T update(I id, Map<String, Object> map) {
        map.remove("create_time");
        map.remove("createTime");
        map.remove("update_time");
        map.remove("updateTime");
        map.remove("deleted");
        return super.update(id, map);
    }
}