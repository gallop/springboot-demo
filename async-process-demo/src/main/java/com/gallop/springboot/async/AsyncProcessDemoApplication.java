package com.gallop.springboot.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncProcessDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncProcessDemoApplication.class, args);
    }

}
