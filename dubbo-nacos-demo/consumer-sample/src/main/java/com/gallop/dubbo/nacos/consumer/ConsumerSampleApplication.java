package com.gallop.dubbo.nacos.consumer;

import com.gallop.dubbo.pojo.User;
import com.gallop.dubbo.service.UserService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

//@SpringBootApplication
@EnableAutoConfiguration
public class ConsumerSampleApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    //这里的负载均衡采用轮循的方式
    @Reference(version = "${user.service.version}",loadbalance = "roundrobin")
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerSampleApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        /*return args -> {
            logger.info("call the userService!!");
            List<User> list = userService.queryAll();
            System.err.println("list-size:"+list.size());
            logger.info("list:"+list);
        };*/
        return new ApplicationRunner(){
            public void run(ApplicationArguments args) throws Exception {
                logger.info("call the userService!!");
                List<User> list = userService.queryAll();
                System.err.println("list-size:"+list.size());
                logger.info("list:"+list);
            }
        };
    }

}
