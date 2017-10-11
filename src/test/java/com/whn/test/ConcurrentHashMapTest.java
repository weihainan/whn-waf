package com.whn.test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weihainan
 * @since 0.1 created on 2017/10/11
 */
public class ConcurrentHashMapTest {

    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public void add(String key) {
        Integer value = map.get(key);  // 第一次 多个线程运行到此的时候 拿到的都是null 那么这几个线程put的都是1 导致计数错误
        if (null == value) {
            map.put(key, 1);
        } else {
            map.put(key, value + 1);
        }
    }

    public Integer get(String key) {
        return map.get(key);
    }

    public static void main(String[] args) throws Exception {
        final ConcurrentHashMapTest test = new ConcurrentHashMapTest();


        new Thread(new Runnable() {
            @Override
            public void run() {
                test.add("1");
                test.add("1");
                test.add("1");
                test.add("2");
                test.add("2");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.add("1");
                test.add("1");
                test.add("2");
                test.add("2");
                test.add("1");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.add("2");
                test.add("1");
                test.add("1");
                test.add("1");
                test.add("1");
            }
        }).start();


        Thread.sleep(2000);

        System.out.println(test.get("1"));
        System.out.println(test.get("2"));
    }
}


// 7
// 5