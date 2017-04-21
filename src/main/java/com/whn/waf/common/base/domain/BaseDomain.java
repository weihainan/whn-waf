package com.whn.waf.common.base.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
@MappedSuperclass
public abstract class BaseDomain<I extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id")
    @Column(length = 64, updatable = false, insertable = true)
    private I id;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }
}
