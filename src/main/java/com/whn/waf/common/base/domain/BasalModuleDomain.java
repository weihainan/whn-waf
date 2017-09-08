package com.whn.waf.common.base.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


/**
 * 与BaseModuleDomain的区别是时间类型不一样
 * @author weihainan
 * @since 0.1 created on 2017/9/8
 */

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BasalModuleDomain<I extends Serializable> extends BaseDomain<I> {

    @Column(name = "update_time")
    @Field("update_time")
    private long updateTime;

    @Column(name = "create_time", updatable = false)
    @Field("create_time")
    private long createTime;

    @Indexed // mongodb
    private boolean deleted;

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
