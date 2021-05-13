package com.gallop.springboot.async.service;

import com.gallop.springboot.async.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * author gallop
 * date 2021-03-26 14:54
 * Description:
 * Modified By:
 */
@Service
public class AsyncTaskService {
    @Autowired
    private MessageDao messageDao;

    /**
     * 从Java 8开始引入了CompletableFuture，它针对Future做了改进，可以传入回调对象，当异步任务完成或者发生异常时，自动调用回调对象的回调方法
     * 最主要是可以提供复杂的
     * CompletableFuture可以指定异步处理流程：
     * thenAccept()处理正常结果；
     * exceptional()处理异常结果；
     * thenApplyAsync()用于串行化另一个CompletableFuture；
     * anyOf()和allOf()用于并行化多个CompletableFuture。
     * 详解请看 https://www.liaoxuefeng.com/wiki/1252599548343744/1306581182447650
     *
     * @param s
     * @return
     */
    @Async(value = "asyncTaskExecutor")
    public CompletableFuture<Object> transTaskForCompletableFuture(String s) {
        Object result = null;
        try {
            result = messageDao.getMessage(s);
            System.out.println(Thread.currentThread().getName() + " 子线程开始执行...result=" + result);
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            return AsyncResult.forExecutionException(e).completable();
        }
        //以下两种都可以正常返回CompletableFuture类型的结果
        //return AsyncResult.forValue(result).completable();
        return CompletableFuture.completedFuture(result);
    }


    public String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.out.println(Thread.currentThread().getName() + " 子线程开始执行...code=601857");
        return "601857";
    }

    
    public Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        Double tmp = 5 + Math.random() * 20;
        System.out.println(Thread.currentThread().getName() + " 子线程开始执行...price=" + tmp);
        return tmp;
    }
}
