package com.gxd.gulimall.search.thread;

import java.util.concurrent.*;
/*
 * 1）、继承Thread
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

        /*execute：参数只能是Runnable，没有返回值
        submit：参数可以是Runnable、Callable，返回值是FutureTask*/
        excutor.submit(new Thread01());
        excutor.execute(new Runable01());
        Future<Integer> submit = excutor.submit(new Callable01());
        Integer integer = submit.get();

        System.out.println("main end.... 返回值："+integer);
    }


      /*1、创建一个固定类型的线程池
            Executors.newFixedThreadPool(10);

        2、直接创建，7个参数
            new ThreadPoolExecutor(corePoolSize,maximumPoolSize，keepAliveime,TimeUnitunit,
                               workQueue,threadFactory,handler);
            corePoolSize：核心线程数，一直存在，一开始只是new 并没有start
            maximumPoolSize：最大线程数量，控制资源
            keepAliveime： 【maximumPoolSize-corePoolSize 超过空闲时间释放线程】
            TimeUnitunit：时间单位
            workQueue：	阻塞队列，只要有线程空闲，就会去队列取出新的任务执行
            threadFactory：线程的创建工厂【可以自定义】
            RejectedExecutionHandler handler：拒绝策略

        3、顺序：
            1、先创建核心线程运行任务
        2、核心线程满放入阻塞队列
	        new LinkedBlockingDeque()默认是Integer的最大值，
            3、阻塞队列满了继续创建线程，最多创建maximumPoolSize个
        4、如果传入了拒绝策略会执行，否则抛出异常
        5、拒绝策略：
            1、丢弃最老的 Rejected
	        2、调用者同步调用，直接调用run方法，不创建线程了 Caller
	        3、直接丢弃新任务 Abort	【默认使用这个】
            4、丢弃新任务，并且抛出异常 Discard*/
    private static void threadPool() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                200,
                10,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

//        常见的4种线程池
//        1、CachedThreadPool：核心线程数是0，如果空闲会回收所有线程【缓存线程池】
//        2、FixedThreadPool：核心线程数 = 最大线程数，【不回收】
//        3、ScheduledThreadPool：定时任务线程池，多久之后执行【可提交核心线程数，最大线程数是Integer.Max】
//        4、SingleThreadPool：核心与最大都只有一个【不回收】,后台从队列中获取任务

        //定时任务的线程池
        ExecutorService service = Executors.newScheduledThreadPool(2);
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
