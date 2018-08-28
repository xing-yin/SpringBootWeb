package com.alany.other;

import org.apache.tomcat.jni.Poll;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by yinxing on 2018/8/8.
 */
public class ThreadPoolExecutor {

    public static void main(String[] args) throws InterruptedException {
        java.util.concurrent.ThreadPoolExecutor poolExecutor = new java.util.concurrent.ThreadPoolExecutor(6, 10, 30,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() +"run");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        poolExecutor.execute(myRunnable);
        poolExecutor.execute(myRunnable);
        poolExecutor.execute(myRunnable);
        System.out.println("======先开3个线程======");
        System.out.println("核心线程数:" +poolExecutor.getCorePoolSize() );
        System.out.println("线程池数:" + poolExecutor.getPoolSize());
        System.out.println("队列任务数:" + poolExecutor.getQueue().size());
        poolExecutor.execute(myRunnable);
        poolExecutor.execute(myRunnable);
        poolExecutor.execute(myRunnable);
        System.out.println("---再开三个---");
        System.out.println("核心线程数" + poolExecutor.getCorePoolSize());
        System.out.println("线程池数" + poolExecutor.getPoolSize());
        System.out.println("队列任务数" + poolExecutor.getQueue().size());
        Thread.sleep(10000);
        System.out.println("----8秒之后----");
        System.out.println("核心线程数" + poolExecutor.getCorePoolSize());
        System.out.println("线程池数" + poolExecutor.getPoolSize());
        System.out.println("队列任务数" + poolExecutor.getQueue().size());
    }

    public static void asynchronousCall(String s){
        //有多种选择 参见:https://blog.csdn.net/qq_20160723/article/details/78131201
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(15);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //待异步调用的代码
                } catch (Exception e) {
                }
            }
        });
    }
}
