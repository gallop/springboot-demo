# redis-lettuce-demo
本demo的主要内容如下：
### 1、介绍redis lettuce 的基本使用
- 连接redis 集群的配置
- lettuce 的配置
- redisTemplate 自己配置LettuceConnectionFactory 连接工厂

### 2、采用拦截器进行缓存控制
#### 2.1. 编写拦截器：RedisCacheInterceptor 实现 HandlerInterceptor接口
#### 2.2. 注册拦截器到Spring容器：
 ```
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RedisCacheInterceptor redisCacheInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.redisCacheInterceptor).addPathPatterns("/**");
    }
}
 ```
> 由于在拦截器中读取了输入流的数据，在request中的输入流只能读取一次，请求进去Controller时，输
  入流中已经没有数据了，导致获取不到数据。

解决方案： 编写HttpServletRequest的包装类：MyServletRequestWrapper，并通过过滤器进行包装request对象：RequestReplaceFilter.java


### 3. 响应结果写入到缓存  

ResponseBodyAdvice是Spring提供的高级用法，会在结果被处理前进行拦截，拦截的逻辑自己实现，这样就可以
实现拿到结果数据进行写入缓存的操作了。

### 4. redis 消息订阅发布的demo
在RedisConfig 配置类中 添加MessageListener，指定订阅redis中 topic发布的消息，定义相关的RedisReceiver类即可。

### 5. redis 分布式锁

#### 5.1 通过RedisTemplate 自己实现锁机制
代码在RedisLock.java 中，主要通过redis nx 命令来实现加锁。
> 注意：在集群模式下，分布式锁会有问题，因为释放锁是用lua 脚本执行的，这种方式在集群的redis会抛异常，  
>另外要切换集群模式还是哨兵模式，需要修改application.yml的配置，另外要在RedisConfig.java类的getConnectionFactory 
>方法配置对应的Configuration，有RedisStandaloneConfiguration、RedisSentinelConfiguration、RedisClusterConfiguration三种

#### 5.2 使用Redisson 客户端实现分布式锁
Redisson是基于Redis，使用Redisson之前，项目必须使用Redis,这里我们采用了Redisson的starter，
结合SpringBoot项目，可以快速的启动，无需过多的配置(redisson 连接哨兵或集群模式需要额外编写config)。需要在pom文件中引入。

```
<!-- 分布式锁【1】引入 redisson 依赖 -->
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>3.12.0</version>
</dependency>
```
第二步，我们在需要使用的类中注入Redisson的客户端`RedissonClient`，如下：
```
//分布式锁【2】自动注入
@Autowired
private RedissonClient redisson;
```
Redisson 分布式锁例子全在ReEntrantLockTest.java 类中
另外Redisson 官方的examples：
https://github.com/redisson/redisson-examples