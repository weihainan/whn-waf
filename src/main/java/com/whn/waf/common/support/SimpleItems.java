package com.whn.waf.common.support;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.Collection;


public class SimpleItems<T> {

    private Collection<T> items;

    public static <T> SimpleItems<T> of(Collection<T> list) {
        SimpleItems<T> items = new SimpleItems<>();
        items.setItems(list);
        return items;
    }

    public static <T, S> SimpleItems<T> of(Collection<S> sList, Function<? super S, T> function) {
        SimpleItems<T> items = new SimpleItems<>();
        items.setItems(Collections2.transform(sList, function));
        return items;
    }


    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
