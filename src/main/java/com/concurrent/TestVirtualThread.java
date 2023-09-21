package com.concurrent;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class TestVirtualThread {
    public static void main(String[] args) {
        ExecutorService es = Executors.newVirtualThreadPerTaskExecutor();
        for (int i=0; i<100000; i++) {
            es.submit(() -> {
                while (true){
//                    System.out.println(Thread.currentThread()+"-"+DateUtil.now());
                    log.info("线程名称：{},时间:{}",Thread.currentThread(),DateUtil.now());
                    System.out.println("哈哈哈");
                    Thread.sleep(0);
                }
            });
        }


        //如果不执行此代码，不会像用户线程一样一直执行，main线程一结束，那么协程就执行完毕，跟go一样，必须要有线程保持运行机制
        while (true){
            System.out.println(Thread.currentThread()+"-"+DateUtil.now());
            try {
                Thread.sleep(1000*30);
                break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
