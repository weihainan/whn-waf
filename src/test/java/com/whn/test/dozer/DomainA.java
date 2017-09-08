package com.whn.test.dozer;

import org.dozer.Mapping;

import java.util.Date;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/8
 */
public class DomainA {

    @Mapping("str")
    private String value;
    @Mapping("today")
    private Date date;
    @Mapping("i")
    private int intValue;
    @Mapping("obj")
    private DomainB object;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public DomainB getObject() {
        return object;
    }

    public void setObject(DomainB object) {
        this.object = object;
    }
}
