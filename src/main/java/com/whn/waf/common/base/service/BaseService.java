package com.whn.waf.common.base.service;

import com.whn.waf.common.base.domain.BaseDomain;
import com.whn.waf.common.base.repository.BaseRepository;
import com.whn.waf.common.context.Module;
import com.whn.waf.common.support.PageableItems;
import com.whn.waf.common.utils.BeanMapUtil;
import com.whn.waf.common.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 针对基础实体的业务逻辑操作基础服务类
 *
 * @since 0.1 created on 2017/3/10.
 */
public abstract class BaseService<T extends BaseDomain<I>, I extends Serializable> {

    @Autowired
    private BaseRepository<T, I> baseRepository;

    protected abstract Module module();

    public T findOne(I id) {
        checkId(id);
        return baseRepository.findOne(id);
    }

    public T findStrictOne(I id) {
        T t = findOne(id);
        if (t == null) {
            throw module().notFound();
        }
        return t;
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public PageableItems<T> findAll(Pageable pageable) {
        Page<T> page = paging(pageable);
        return PageableItems.of(page.getContent(), page.getTotalElements());
    }

    public Page<T> paging(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    public T add(T t) {
        ValidatorUtil.validateAndThrow(t);
        beforeAdd(t);
        return baseRepository.save(t);
    }

    protected void beforeAdd(T t) {

    }

    public boolean exites(I id) {
        return findOne(id) != null;
    }

    protected void delete(T t) {
        if (t == null) {
            throw module().notFound();
        }
        beforeDelete(t);
        baseRepository.delete(t);
    }

    protected void beforeDelete(T t) {

    }

    public void delete(I id) {
        delete(findStrictOne(id));
    }

    protected T update(T t) {
        checkId(t.getId());
        if (!exites(t.getId())) {
            throw module().notFound();
        }
        return baseRepository.save(t);
    }

    public T update(I id, Map<String, Object> params) {
        T oldDomain = findStrictOne(id);
        params.remove("id");
        T newDomain = BeanMapUtil.createDomainFromOldAndMap(oldDomain, params);
        ValidatorUtil.validateAndThrow(newDomain);
        beforeAdd(oldDomain, newDomain);
        return update(newDomain);
    }

    protected void beforeAdd(T oldDomain, T newDomain) {

    }

    protected void checkId(I id) {
        if (id == null) {
            throw module().nullId();
        }
    }
}
