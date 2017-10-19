package com.whn.waf.common.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.Collections;

/**
 * 用于响应集合和总数
 *
 * @param <T> 响应实体的类型
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
public class PageableItems<T> extends SimpleItems<T> {

    @JsonProperty("total")
    private long total;


    public static <T> PageableItems<T> of(Collection<T> list, long total) {
        PageableItems<T> items = new PageableItems<>();
        items.setItems(list);
        items.setTotal(total);
        return items;
    }

    public static <T, S> PageableItems<T> of(Collection<S> sList, long total, Function<? super S, T> function) {
        PageableItems<T> items = new PageableItems<>();
        items.setItems(Collections2.transform(sList, function));
        items.setTotal(total);
        return items;
    }

    public static <T> PageableItems<T> emptyItems() {
        PageableItems<T> items = new PageableItems<>();
        items.setTotal(0);
        items.setItems(Collections.<T>emptyList());
        return items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
