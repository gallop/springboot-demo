package com.gallop.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * author gallop
 * date 2021-06-09 9:56
 * Description:
 * AutoCloseable接口:
 * 当一个资源类实现了该接口close方法，在使用try-catch-resources语法创建的资源抛出异常后，JVM会自动调用close 方法进行资源释放，
 * 当没有抛出异常正常退出try-block时候也会调用close方法。
 * 像数据库链接类Connection,io类InputStream或OutputStream都直接或者间接实现了该接口。
 *
 */
@Slf4j
public class RedisLock implements AutoCloseable {
    private RedisTemplate redisTemplate;
    private String key;
    private String value;
    //单位：秒
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate,String key,int expireTime){
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.expireTime=expireTime;
        this.value = UUID.randomUUID().toString();
        this.value += "{yes}";
        System.err.println("key="+this.key);
        System.err.println("value="+this.value);
    }

    /**
     * 获取分布式锁
     * @return
     */
    public boolean getLock(){
        RedisCallback<Boolean> redisCallback = connection -> {
            //设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            //设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            //序列化key
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            //序列化value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            //执行setnx操作
            Boolean result = connection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };

        //获取分布式锁
        Boolean lock = (Boolean)redisTemplate.execute(redisCallback);
        return lock;
    }

    public boolean unLock() {
        // 通过调用一段lua 来解锁，如果锁存在，则删除锁
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script,Boolean.class);
        List<String> keys = Arrays.asList(key);

        //Boolean result = (Boolean)redisTemplate.execute(redisScript, keys, value);
        Boolean result = (Boolean)redisTemplate.execute(redisScript, keys, value);
        log.info("释放锁的结果："+result);
        return result;
    }

    @Override
    public void close() throws Exception {
        log.info("进入close方法中。。。。");
        unLock();
    }
}
