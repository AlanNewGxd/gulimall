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
//         CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
//             System.out.println("当前线程：" + Thread.currentThread().getId());
//             int i = 10 / 0;
//             System.out.println("运行结果：" + i);
//             return i;
//         }, excutor).handle((result,thr) -> {
//             if (result != null) {
//                 return result * 2;
//             }
//             if (thr != null) {
//                 System.out.println("异步任务成功完成了...结果是：" + result + "异常是：" + thr);
//                 return 0;
//             }
//             return 0;
//         });
//
//        Integer integer = future2.get();
//        System.out.println("main......end....."+integer);

        /**
         * 线程串行化
         * 1、thenRunL：不能获取上一步的执行结果
         * 2、thenAcceptAsync：能接受上一步结果，但是无返回值
         * 3、thenApplyAsync：能接受上一步结果，有返回值
         *
         */
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, excutor).thenApplyAsync(res -> {
//            System.out.println("任务2启动了..." + res);
//            return "Hello" + res;
//        }, excutor);
//        System.out.println("main......end....." + future.get());


//        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(()->{
//            System.out.println("任务1 start..");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("任务1 end..");
//            return 1+1;
//        });
//
//        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
//            System.out.println("任务2 start..");
//            System.out.println("任务2 end..");
//            return "hello";
//        });

        // 两个任务都完成才执行任务3
//        CompletableFuture<String> future3 = future1.thenCombineAsync(future2, (result1, result2) -> {
//            return "任务3 ：组合前两个任务的返回值返回 --" + result1 + "---" + result2;
//        }, excutor);

        // 任一任务完成就可以执行任务3【返回值是future1的泛型】
//        CompletableFuture<String> future3 = future1.applyToEitherAsync(future2, (result) -> {
//            return "任务3 ：组合先执行完的任务的结果 --" + result;
//        }, excutor);
//        System.out.println("main end.... 返回值：" + future3.get());

        /*多任务组合*/
        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(()->{
            System.out.println("查询商品的图片信息");
            return "hello.jpg";
        },excutor);

        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(()->{
            System.out.println("查询商品的属性");
            return "黑色+256G";
        },excutor);

        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
                System.out.println("查询商品介绍");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "华为";
        },excutor);

//        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
//        completableFuture.get();
//        System.out.println("main end.... "+futureImg.get()+"->"+futureAttr.get()+"->"+futureDesc.get());

        //有一个执行成功就返回
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
        Object o = objectCompletableFuture.get();
        System.out.println("main end.... "+o);
    }

}
