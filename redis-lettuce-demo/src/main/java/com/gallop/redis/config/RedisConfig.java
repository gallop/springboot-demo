package com.gallop.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.gallop.redis.service.RedisReceiver;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * author gallop
 * date 2021-06-02 15:04
 * Description:
 * Modified By:
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    //@Value("${spring.redis.cluster.maxRedirects:3}")
    //private int maxRedirects;

    @Value("${redis.refreshTime:5}")
    private int refreshTime;

     /**
      * date @2021-06-07
      * Description: redis消息订阅发布的相关配置
      **/
    @Bean
    RedisMessageListenerContainer container(LettuceConnectionFactory lettuceConnectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);

        //可以添加多个 messageListener
        //可以对 messageListener对应的适配器listenerAdapter  指定本适配器 适配的消息类型  是什么
        //在发布的地方 对应发布的redisTemplate.convertAndSend("user-queue",msg);  那这边的就对应的可以消费到指定类型的 订阅消息
        container.addMessageListener(listenerAdapter, new PatternTopic("user-queue"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "receiveMessage");
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        /*RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;*/

        //设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance , ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        //om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
      * date @2021-06-02
      * Description: 获取缓存连接
      * return:
      **/
    @Bean(value = "lettuceConnectionFactory")
    public LettuceConnectionFactory getConnectionFactory(GenericObjectPoolConfig genericPoolConfig) {
        //单机模式
        /*RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());
        configuration.setDatabase(redisProperties.getDatabase());
        configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, getPoolConfig(genericPoolConfig));*/
        //哨兵模式
        //RedisSentinelConfiguration configuration1 = new RedisSentinelConfiguration();
        //集群模式
        //Map<String, Object> source = new HashMap<>(8);
        //source.put("spring.redis.cluster.nodes", environment.getProperty("spring.redis.cluster.nodes"));
        //RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        clusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        //ClusterConfiguration.setPassword(environment.getProperty("spring.redis.password"));


        LettuceConnectionFactory factory = new LettuceConnectionFactory(clusterConfiguration, getPoolConfig(genericPoolConfig));
        //factory.setShareNativeConnection(false);//是否允许多个线程操作共用同一个缓存连接，默认true，false时每个操作都将开辟新的连接
        return factory;
    }

    @Bean
    //@ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    @Scope(value = "prototype")
    public GenericObjectPoolConfig genericPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        config.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        config.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        config.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        return config;
    }

     /**
      * 获取缓存连接池
      **/
    @Bean
    public LettucePoolingClientConfiguration getPoolConfig(GenericObjectPoolConfig genericPoolConfig) {
        System.err.println("getMaxTotal==="+genericPoolConfig.getMaxTotal());
        //可解决集群信息已变更(添加或停节点)，RedisTemplate无法访问节点
        //支持自适应集群拓扑刷新和静态刷新源
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions =  ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh()
                .enableAllAdaptiveRefreshTriggers()
                .refreshPeriod(Duration.ofSeconds(refreshTime))
                .build();

        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions).build();

        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
                .poolConfig(genericPoolConfig)
                .commandTimeout(redisProperties.getTimeout())
                .shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout())
                //从优先，读写分离，读从可能存在不一致，最终一致性CP
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .clientOptions(clusterClientOptions)
                .build();

        return pool;
    }
}
