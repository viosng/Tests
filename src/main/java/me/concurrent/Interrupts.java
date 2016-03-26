package me.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.IntStream.range;

/**
 * Created by viosn_000 on 28.01.2016.
 */
public class Interrupts {

    public static Runnable RUNNABLE(String s) {
        return () -> {
            try {
                Thread.sleep(1000);
                System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = () -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted!!");
                    latch.countDown();
                    return;
                } else {
                    System.out.println("Not Interrupted");
                }
                System.out.println(range(1, 100000).map(i -> ThreadLocalRandom.current().nextInt()).sum());
            }
        };
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(runnable);
        Thread.sleep(3000);
        completableFuture.cancel(true);
        latch.await();
        System.out.println("finish");
    }
}
