package com.gallop.springboot.async;

import ch.qos.logback.core.util.TimeUtil;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * author gallop
 * date 2021-05-11 15:16
 * Description:
 * Modified By:
 */
public class Test {
    public static void thenAccept() throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new Random().nextInt(10);
            }
        });
        future.thenAccept(integer -> {
            System.out.println(integer);
        });
        System.out.println("call the method complete!!");
        //会阻塞到异步方法执行返回结果
        future.get();
    }
    private static void applyToEither() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = new Random().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("f1="+t);
                return t;
            }
        });
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = new Random().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("f2="+t);
                return t;
            }
        });

        CompletableFuture<Integer> result = f1.applyToEither(f2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer t) {
                System.out.println(t);
                return t * 2;
            }
        });

        System.out.println("result:"+result.get());
    }
    public static void main(String[] args) throws Exception {
        Test.applyToEither();
    }
}
