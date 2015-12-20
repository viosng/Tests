package me.concurrent;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by vio on 24.08.2015.
 */
public class Futures {

    public static <T> T print(T value, String append) {
        System.out.printf("%s - [%s]: %s - %s\n", new Date(), Thread.currentThread().getName(), append, value);
        return value;
    }



    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory() {
            private final AtomicLong counter = new AtomicLong();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("MyThread#" + counter.getAndIncrement());
                return thread;
            }
        });
        CompletableFuture
                .completedFuture((long) 1)
                .thenApply(l -> print(l + 1, "0)"))
                .thenApplyAsync(l -> print(l + 1, "1)"), executorService)
                .thenCombineAsync(CompletableFuture.completedFuture((long) 10), (l, r) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return  l * r;
                }, executorService)
                .thenApply(l -> print(l + 1, "2)"))
                .thenAccept(v -> {
                    print(v, "finish");
                    latch.countDown();
                });
        latch.await();
        executorService.shutdown();
    }
}
