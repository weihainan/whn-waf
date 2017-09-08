package com.whn.test.dozer;

import org.dozer.Mapping;

import java.util.Date;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/8
 */
public class DomainB {

    private String str;
    private Date today;
    private int i;
    private DomainB obj;


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public DomainB getObj() {
        return obj;
    }

    public void setObj(DomainB obj) {
        this.obj = obj;
    }
}
