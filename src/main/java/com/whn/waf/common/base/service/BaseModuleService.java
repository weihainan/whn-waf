package com.whn.waf.common.base.service;

import com.whn.waf.common.base.domain.BaseModuleDomain;
import com.whn.waf.common.base.repository.BaseModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 针对基础业务实体的业务逻辑操作基础服务类
 *
 * @since 0.1 created on 2017/3/10.
 */
public abstract class BaseModuleService<T extends BaseModuleDomain<I>, I extends Serializable>
        extends BaseService<T, I> {

    @Autowired
    private BaseModuleRepository<T, I> baseModuleRepository;

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
        return baseModuleRepository.findByDeletedIsFalse();
    }

    @Override
    public Page<T> paging(Pageable pageable) {
        return baseModuleRepository.findByDeletedIsFalse(pageable);
    }

    // 添加或内部更新
    @Override
    public T add(T t) {
        // 避免外部设置
        //当id为外部指定是@CreateDate无法自动设置
        t.setCreateTime(new Date());
        t.setUpdateTime(null);
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
        t.setUpdateTime(new Date());
        baseModuleRepository.save(t);
    }

    @Override
    public T update(I id, Map<String, Object> map) {
        // 不需要外部设置
        map.remove("create_time");
        map.remove("createTime");
        map.remove("update_time");
        map.remove("updateTime");
        map.remove("deleted");
        return super.update(id, map);
    }

}
