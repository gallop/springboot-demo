package com.gallop.springboot.async.controller;

import com.gallop.springboot.async.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * author gallop
 * date 2021-03-26 15:16
 * Description:
 * Modified By:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private AsyncTaskService asyncTaskService;

    @GetMapping("/getList")
    public String getUserData() {
        AtomicReference<String> result = new AtomicReference<String>();
        String s = "xiaoming";
        System.out.println(Thread.currentThread().getName() + " call getUserData...");
        CompletableFuture<Object> cf1 = asyncTaskService.transTaskForCompletableFuture(s);

        /*//异步回调结果：
        cf1.thenAccept((rsp) -> {
            System.out.println("name: " + rsp);
            result.set((String) rsp);
        });*/


        //阻塞等待结果：
        try {
            String r = (String) cf1.get();
            System.out.println("rr=" + r);
            result.set(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /*System.out.println("before return value!!");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return result.get();
    }

    /**
     * date @2021-03-26
     * Description: 第二个异步任务依赖于第一个异步任务的结果；
     * Param:
     * return:
     **/
    @GetMapping("/getPrice")
    public double getStockPrice() {
        double result = 0;
        // 第一个任务: 根据名称获取股票代码:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
            return asyncTaskService.queryCode("中国石油");
        });

        // cfQuery成功后继续执行下一个任务:

        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            System.out.println("code=" + code);
            return asyncTaskService.fetchPrice(code);
        });
        cfFetch.thenAccept((r) -> {
            System.out.println("======price: " + r);
        });
        try {
            result = (Double) cfFetch.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }
}
