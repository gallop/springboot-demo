package com.gallop.redis.lock;

import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author gallop
 * date 2021-06-10 11:28
 * Description:
 * Modified By:
 */
@Component
public class ReEntrantLockTest {
    private static final int THREAD_NUMBER = 10;
    @Resource
    private RedissonClient redissonClient;

    // 可重入锁
    public void reentrantLock() throws InterruptedException{
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);
        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= THREAD_NUMBER; i++) {
            int finalI = i;
            executorService.submit(() -> {
                final RLock lock = redissonClient.getLock("reentrantLock");
                try {
                    //lock.tryLock(18, 4, TimeUnit.SECONDS)
                    /*if (lock.tryLock(18, 4, TimeUnit.SECONDS)) {
                        //System.out.println("获得锁 Thread- " + finalI);
                        System.err.println("获得锁 线程："+Thread.currentThread().getName()+"-"+finalI);
                        Thread.sleep(1000);
                    }else {
                        System.err.println("线程："+Thread.currentThread().getName()+"获取锁失败");
                    }*/
                    lock.lock(2,TimeUnit.SECONDS);
                    System.err.println("获得锁 线程："+Thread.currentThread().getName()+"-"+finalI);
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("释放锁 "+Thread.currentThread().getName()+"- " + finalI);
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("结束...");
    }

    // 可重入锁
    public void reentrantLock2() throws InterruptedException{
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);
        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= THREAD_NUMBER; i++) {
            int finalI = i;
            executorService.submit(() -> {
                final RLock lock = redissonClient.getLock("reentrantLock");
                boolean hasLock = false;
                try {
                    //lock.tryLock(18, 4, TimeUnit.SECONDS)
                    if (hasLock = lock.tryLock(6, 4, TimeUnit.SECONDS)) {
                        //System.out.println("获得锁 Thread- " + finalI);
                        System.err.println("获得锁 线程："+Thread.currentThread().getName()+"-"+finalI);
                        Thread.sleep(1000);
                    }else {
                        System.err.println("线程："+Thread.currentThread().getName()+"获取锁失败");
                    }
                    System.out.println(Thread.currentThread().getName()+"count==="+countDownLatch.getCount());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.err.println("into finally-"+Thread.currentThread().getName());
                    if(hasLock){
                        System.out.println(Thread.currentThread().getName()+"-is locked!");
                        lock.unlock();
                    }

                    System.out.println("释放锁 "+Thread.currentThread().getName()+"- " + finalI);
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        System.out.println("结束...");
    }

    //常用于协调多个线程，互相等待的场景。在全部线程执行完成后，结束阻塞
    public void countDownLatchLock() throws InterruptedException {
        //final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("倒数同步锁");
        System.out.println("============================================================");
        final RCountDownLatch countDownLatch = redissonClient.getCountDownLatch("countDownLatchLock");
        countDownLatch.trySetCount(THREAD_NUMBER);
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    System.out.println("取得锁 Thread-" + finalI);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("释放锁 Thread- " + finalI);
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("结束...");
        System.out.println();
    }
     /**
      * date 2021-06-11 10:37
      *  信号量（Semaphore）
      * ​对于同一个资源的并发度做的锁限制，在实例化的时候可以指定许可的数量。
      * 每个并发线程要持有一个许可，当许可都被领取空时，后面的线程就会阻塞。常用于做并发度的流控
      **/
    public void semaphoreLock() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("测试信号量锁");
        System.out.println("============================================================");
        final RSemaphore semaphore = redissonClient.getSemaphore("semaphoreLock");
        //只有两个并发度，两个线程同时执行
        //当redis中已经有值（key 为semaphoreLock），此行代码无效
        semaphore.trySetPermits(5);
        System.err.println("availableNum1="+semaphore.availablePermits());
        //清除许可数
        semaphore.drainPermits();
        System.err.println("availableNum1="+semaphore.availablePermits());
        //增加许可数，在原来基础上加上给定的值
        semaphore.addPermits(2);
        System.err.println("availableNum="+semaphore.availablePermits());
        //semaphore.addPermits(1);
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]尝试获取Semaphore锁");
                    semaphore.acquire();
                    //System.out.println("取得锁 Thread-" + finalI);
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "-" +finalI+"]成功获取到了Semaphore锁，开始工作");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    System.err.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]释放Semaphore锁");
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("结束...");
        System.out.println();
    }

     /**
      * date 2021-06-11 13:47
      * Description:
      * 公平锁（fairLock）
      * 按申请前后的顺序获取到锁，先去申请的线程先获取到锁
      **/
    public void fairLock() throws InterruptedException{
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);
        final ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("测试公平锁");
        System.out.println("============================================================");
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            int finalI = i;
            executorService.submit(() -> {
                final RLock lock = redissonClient.getFairLock("fairLock");
                try {
                    System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]尝试获取Semaphore锁");
                    if (lock.tryLock(100, 4, TimeUnit.SECONDS)) {
                        Thread.currentThread().setName("thread-"+finalI);
                        //System.out.println("获得锁 Thread- " + finalI);
                        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "-" +finalI+"]成功获取到了Semaphore锁，开始工作");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.err.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]释放Semaphore锁");
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("结束...");
        System.out.println();
    }
}
