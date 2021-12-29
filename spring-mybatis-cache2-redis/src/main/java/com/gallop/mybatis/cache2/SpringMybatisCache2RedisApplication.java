package com.gallop.mybatis.cache2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.gallop.mybatis.cache2.mapper")
public class SpringMybatisCache2RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMybatisCache2RedisApplication.class, args);
    }

}
