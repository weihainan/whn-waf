package com.whn.waf.common.thread;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 串行线程池
 * <p>
 * 何为串行线程池呢？
 * 也就是说，我们的Runnable对象应该有个排队的机制，它们顺序从队列尾部进入，并且从队列头部选择Runnable进行执行。
 * <p>
 * 既然我们有了思路：
 * 1. 那我们就考虑一下所需要的数据结构？
 * 既然是从队列尾部插入Runnable对象，从队列头部执行Runnable对象，我们自然需要一个队列。
 * Java的SDK已经给我们提供了很好的队列数据结构，例如双端队列：ArrayDeque<Runnable>。
 * <p>
 * 因为涉及到线程的执行，那我们首先就需要有一个合适的线程池，使用ThreadPoolExecutor类即可构造。
 * <p>
 * 既然是串行执行，那如何保持串行机制呢？
 * 我们可以通过try和finally机制，我们将传入的Runnable对象重新封装成一个新的Runnable对象，
 * 在新的Runnable的run方法的try块中执行Runnable的run方法，在finally中调用执行队列头部Runnable对象出队列，并放入线程池执行的方法。
 *
 * @author weihainan
 * @since 0.1 created on 2017/9/14
 */
public class SerialThreadExecutor {

    private Runnable mActive;
    private ArrayDeque<Runnable> mArrayDeque = new ArrayDeque<>();

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingDeque<>(128);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Serial thread #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadPoolExecutor THREAD_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    public synchronized void execute(final Runnable r) {
        mArrayDeque.offer(new Runnable() {
            @Override
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        // 第一次入队列时mActivie为空，因此需要手动调用scheduleNext方法
        if (mActive == null) {
            scheduleNext();
        }
    }

    private void scheduleNext() {
        if ((mActive = mArrayDeque.poll()) != null) {
            THREAD_EXECUTOR.execute(mActive);
        }
    }


    public static void main(String[] args) {
        SerialThreadExecutor serialExecutor = new SerialThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int j = i;
            serialExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("The num is :" + (j + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
