package com.gxd.gulimall.search.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guxiaodong
 * @version 1.0
 * @title CompletableFuture异步编排
 * @date 2021/7/22 10:34
 */
public class ThreadTest2 {

    public static ExecutorService excutor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main......start.....");
//         CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//             System.out.println("当前线程：" + Thread.currentThread().getId());
//             int i = 10 / 2;
//             System.out.println("运行结果：" + i);
//         }, excutor);

        //方法完成后的感知处理
//        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, excutor).whenComplete((result,exception) -> {
//            //虽然能得到异常信息，但是没法修改返回数据
//            System.out.println("异步任务成功完成了...结果是：" + result + "异常是：" + exception);
//        }).exceptionally(throwable -> {
//            //可以感知异常，同时返回默认值
//            return 10;
//        });
//
//        Integer integer = future1.get();
//        System.out.println("main......end....."+integer);

        /**
         * 方法执行完后端处理 handle方法
         */
         CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
             System.out.println("当前线程：" + Thread.currentThread().getId());
             int i = 10 / 0;
             System.out.println("运行结果：" + i);
             return i;
         }, excutor).handle((result,thr) -> {
             if (result != null) {
                 return result * 2;
             }
             if (thr != null) {
                 System.out.println("异步任务成功完成了...结果是：" + result + "异常是：" + thr);
                 return 0;
             }
             return 0;
         });

        Integer integer = future2.get();
        System.out.println("main......end....."+integer);
    }
}
