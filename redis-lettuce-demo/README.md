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
