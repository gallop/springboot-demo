package com.gallop.mybatis.interceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages="com.gallop.mybatis.interceptor.mapper")
@ComponentScan(basePackages= {"com.gallop"})
public class MybatisInterceptorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisInterceptorDemoApplication.class, args);
    }

}
