package cn.hbwy.ftptohdfs.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类
 */
public class ThreadPool {
    public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);

    public ThreadPool() {
    }

    public static ExecutorService getThreadPool() {
        return executorService;
    }

}
