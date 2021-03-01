package com.xj.glmall.search.thread;

import java.util.concurrent.*;

public class CompletableFutureTest {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue)*/
        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
            System.out.println("execute supply task");
            return "this supply task";
        }, executor);
        CompletableFuture<Void> voidCompletableFuture = exceptionally.runAfterBothAsync(exceptionally, () -> {
            System.out.println("execute task");
        }, executor);
        System.out.println(voidCompletableFuture.get());

    }
}
