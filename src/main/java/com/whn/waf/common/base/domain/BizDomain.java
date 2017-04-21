package com.whn.waf.common.base.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public abstract class BizDomain<I extends Serializable> extends BaseDomain<I> {

    @LastModifiedDate
    private Date updateTime;

    @CreatedDate
    @Column(updatable = false)
    private Date createTime;

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
