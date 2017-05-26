package com.whn.waf.common.module.domain;

import com.whn.waf.common.base.domain.BaseDomain;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/5/26.
 */
@Document(collection = "application")
public class Application extends BaseDomain<String> {

    @NotBlank(message = "应用名称不能为空！")
    @Length(max = 30, message = "应用名称长度不能超过30.")
    @Indexed(unique = true)
    private String name;

    @Length(max = 1000, message = "应用简介长度不能超过1000.")
    private String desc;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;


// ====================== GETTER/SETTER =======================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
