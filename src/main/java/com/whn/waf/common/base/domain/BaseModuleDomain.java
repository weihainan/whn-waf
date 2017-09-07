package com.whn.waf.common.base.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseModuleDomain<I extends Serializable> extends BaseDomain<I> {

    @LastModifiedDate
    @Column(name="update_time")
    @Field("update_time")
    private Date updateTime;

    @CreatedDate
    @Column(name="create_time", updatable = false)
    @Field("create_time")
    private Date createTime;

    @Indexed // mongodb
    private boolean deleted;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
