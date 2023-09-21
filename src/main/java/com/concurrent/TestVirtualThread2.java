package com.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于打印平台线程，其实比实际的小，并发百万也就18个物理线程左右
 *  executor.close();会等待协程任务执行完成，如果不调用close()方法，那么如果jvm没有常驻用户线程，那么jvm就会结束进程
 */
public class TestVirtualThread2 {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
//        // 虚拟线程
        ExecutorService executor =  Executors.newVirtualThreadPerTaskExecutor();
        // 使用平台线程
        // ExecutorService executor =  Executors.newFixedThreadPool(200);
        for (int i = 0; i < 1000000; i++) {
            executor.submit(() -> {

                    // 线程睡眠 0.5 s，模拟业务处理
                    while (true){
//                        System.out.println(Thread.currentThread());
                        Thread.sleep(0);
                    }

            });
        }
        executor.close();


        new Thread(()->{
            int oldTheadNum=-1;
            while (true){
                try {
                    ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                    ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
                    if (threadInfo.length!=oldTheadNum){
                        oldTheadNum=threadInfo.length;
                        System.out.println("max：" + oldTheadNum + " platform thread/os thread");
                        System.out.printf("totalMillis：%dms\n", System.currentTimeMillis() - start);
                    }

                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();


    }

}


