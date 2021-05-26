package com.gallop.dubbo;

import com.gallop.dubbo.pojo.User;
import com.gallop.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * author gallop
 * date 2021-05-26 9:50
 * Description:
 * Modified By:
 */
@EnableAutoConfiguration
public class DubboConsumerBootstrap {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference(version = "${user.service.version}")
    private UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class).close();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            logger.info("call the userService!!");
            List<User> list = userService.queryAll();
            System.err.println("list-size:"+list.size());
            logger.info("list:"+list);
        };
    }
}
