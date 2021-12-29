package com.gallop.mybatis.cache2.cache;

import com.gallop.mybatis.cache2.utils.Md5Util;
import com.gallop.mybatis.cache2.contex.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * author gallop
 * date 2021-12-04 8:22
 * Description: 使用Redis实现Mybatis二级缓存
 * Modified By:
 */
@Slf4j
public class MybatisRedisCache implements Cache {
    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private RedisTemplate<String, Object> redisTemplate;

    private String id;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.info("Redis Cache id " + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        String keyStr = this.id + "_" + Md5Util.getMD5(key.toString());
        RedisTemplate redisTemplate = getRedisTemplate();
        if (value != null) {
            // 向Redis中添加数据
            redisTemplate.opsForValue().set(keyStr, value, 180, TimeUnit.MINUTES);
        }
    }

    @Override
    public Object getObject(Object key) {
        String keyStr = this.id + "_" + Md5Util.getMD5(key.toString());
        RedisTemplate redisTemplate = getRedisTemplate();
        try {
            if (key != null) {
                Object obj = redisTemplate.opsForValue().get(keyStr);
                return obj;
            }
        } catch (Exception e) {
            //logger.error("redis ");
            log.error("mybatis-redis-error:"+e.getMessage());
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        String keyStr = this.id + "_" + Md5Util.getMD5(key.toString());
        RedisTemplate redisTemplate = getRedisTemplate();
        try {
            if (key != null) {
                redisTemplate.delete(keyStr);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        try {
            Set<String> keys = redisTemplate.keys("" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate = getRedisTemplate();
        Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
