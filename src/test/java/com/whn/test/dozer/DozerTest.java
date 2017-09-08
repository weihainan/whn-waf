package com.whn.test.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Test;

import java.util.Date;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/8
 */
public class DozerTest {

    @Test
    public void test() {
        DomainB obj = new DomainB();
        obj.setI(10);
        obj.setStr("11");
        obj.setToday(new Date());
        obj.setObj(obj);

        DomainB source = new DomainB();
        source.setI(10);
        source.setStr("11");
        source.setToday(new Date());
        source.setObj(obj);

        Mapper mapper = new DozerBeanMapper();

        // Mapping注解要在target上
        DomainA target = mapper.map(source, DomainA.class);

        System.out.println(target);
    }
}
