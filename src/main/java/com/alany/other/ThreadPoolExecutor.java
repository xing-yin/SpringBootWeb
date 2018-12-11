package com.alany.other;

import com.alany.common.http.HttpClientUtil;
import com.alany.common.http.HttpConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by yinxing on 2018/8/8.
 */
public class ThreadPoolExecutor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异步调用上报接口
     *
     * @param url
     * @param stringMap
     */
    public void asynchronousCall(String url, Map<String, Object> stringMap) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("asynchronousCall-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new java.util.concurrent.ThreadPoolExecutor(15, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClientUtil.doGet(url, stringMap, HttpConstants.HttpClientConfig.DEFAULT);
                } catch (Exception e) {
                    logger.info("http上报solr失败！");
                }
            }
        });
        pool.shutdown();//gracefully shutdown

//        不建议这么做
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(15);
//        fixedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HttpClientUtil.doGet(url, stringMap, HttpConstants.HttpClientConfig.DEFAULT);
//                } catch (Exception e) {
//                    logger.info("http上报solr失败！");
//                }
//            }
//        });
    }


    public static void main(String[] args) throws InterruptedException {
        java.util.concurrent.ThreadPoolExecutor poolExecutor = new java.util.concurrent.ThreadPoolExecutor(6, 10, 30,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "run");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        poolExecutor.execute(myRunnable);
        poolExecutor.execute(myRunnable);
        poolExecutor.execute(myRunnable);
        System.out.println("======先开3个线程======");
        System.out.println("核心线程数:" + poolExecutor.getCorePoolSize());
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

    public static void asynchronousCall(String s) {
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
