package com.gxd.gulimall.search.thread;

import java.util.concurrent.*;
/*
 * ）、继承Thread
 * 2）、实现 Runnable接口
 * 3）、实现 Callable接口+FutureTask（可以拿到返回结果，可以处理异常）
 * 4）、线程池1
 **/
/*区别;
        1、2不能得返到回值。3可以获取返回值
        1、2、3都不能控制资源
        4可以控制资源，性能稳定，不会一下子所有线程一起运行*/
public class ThreadTest {

    public static ExecutorService excutor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main start ....");

        //执行Thread异步线程
//         Thread thread = new Thread01();
//         thread.start();

        //执行Runable异步线程
//         new Thread(new Runable01()).start();

        //执行Callable异步线程
//         FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//         new Thread(futureTask).start();
//         //阻塞式等待
//         Integer integer = futureTask.get();

//        System.out.println("main end.... 返回值："+integer);

        excutor.submit(new Thread01());
        excutor.execute(new Runable01());
        Future<Integer> submit = excutor.submit(new Callable01());
        Integer integer = submit.get();

        System.out.println("main end.... 返回值："+integer);
    }



    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }


    public static class Runable01 implements Runnable {
        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("运行结果：" + i);
        }
    }


    public static class Callable01 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 10;
            System.out.println("运行结果：" + i);
            return i;
        }
    }
}
