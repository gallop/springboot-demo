package com.gallop.redis;

import com.gallop.redis.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;

/**
 * author gallop
 * date 2021-06-02 16:57
 * Description:
 * Modified By:
 */
@SpringBootApplication
@Slf4j
public class RedisDemoApplication {
    @Autowired
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

     /**
      * date @2021-06-07
      * Description:
      * Param: 测试往redis消息订阅发布：发布消息测试
      * return:
      **/
    @Bean
    public ApplicationRunner runner() {
        return args -> {
            User user = User.builder().name("gallop").age(30).createTime(new Date()).build();
            //redis 队列发送消息
            redisTemplate.convertAndSend("user-queue",user);
            System.err.println("Publisher sendes Topic... ");
        };
    }
}
