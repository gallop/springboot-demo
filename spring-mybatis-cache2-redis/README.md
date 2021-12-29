## spring-mybatis-cache2-redis

本demo 介绍如何打开mybatis 二级缓存，并使用redis做为缓存容器。要点如下：

1、RedisConfig 配置，主要是获取redisTemplate，这里需要注意的是java 8 中的 LocalDate、LocalDateTime系列化的问题。

2、实现mybatis的接口Cache，实现类的路径为：com.gallop.mybatis.cache2.cache.MybatisRedisCache

需要注意的是，这里的redisTemplate 并不能直接用@Autowired 去获取，因为这个类不知spring 容器管理范围内，因此获取不到redisTemplate，而是需要通过另外一个工具类SpringContextHolder从spring容器中获得。

3、在需要开启二级缓存的mybatis xml 文件开头 添加如下配置：

````xml
   <!-- 开启基于redis的二级缓存 -->
    <cache type="com.gallop.mybatis.cache2.cache.MybatisRedisCache" eviction="LRU" flushInterval="7200000" 			readOnly="false">
    </cache>
````



