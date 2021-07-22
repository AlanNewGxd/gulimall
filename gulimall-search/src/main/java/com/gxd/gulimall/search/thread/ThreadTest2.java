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

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, excutor);

        Integer integer = future1.get();

        System.out.println("main......end....."+integer);
    }
}
