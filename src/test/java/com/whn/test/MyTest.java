package com.whn.test;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/4/25.
 */
public class MyTest {

    @Test
    public void test01(){

        //构造方法被私有化了。
        Runtime myRun = Runtime.getRuntime();
        System.out.println("已用内存" + myRun.totalMemory());
        System.out.println("最大内存" + myRun.maxMemory());
        System.out.println("可用内存" + myRun.freeMemory());
        String i = "";
        long start = System.currentTimeMillis();
        System.out.println("浪费内存中.....");
        for(int j = 0;j < 10000;j++)
        {
            i += j;
        }
        long end = System.currentTimeMillis();
        System.out.println("执行此程序总共花费了" + ( end - start )+ "毫秒");
        System.out.println("已用内存" + myRun.totalMemory());
        System.out.println("最大内存" + myRun.maxMemory());
        System.out.println("可用内存" + myRun.freeMemory());
        myRun.gc();
        System.out.println("清理垃圾后");
        System.out.println("已用内存" + myRun.totalMemory());
        System.out.println("最大内存" + myRun.maxMemory());
        System.out.println("可用内存" + myRun.freeMemory());
    }

//    已用内存255852544
//    最大内存3797417984
//    可用内存242408504
//    浪费内存中.....
//    执行此程序总共花费了402毫秒
//    已用内存716701696
//    最大内存3797417984
//    可用内存650368104
//    清理垃圾后
//    已用内存1253048320
//    最大内存3797417984
//    可用内存1230493968


    @Test
    public void testSet(){
        Set set = Sets.newHashSet();
        System.out.println(2 << 3);
    }
}
