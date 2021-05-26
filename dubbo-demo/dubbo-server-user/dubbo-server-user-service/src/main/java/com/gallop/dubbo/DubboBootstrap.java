package com.gallop.dubbo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * author gallop
 * date 2021-05-26 9:50
 * Description:
 * Modified By:
 */
@EnableAutoConfiguration
public class DubboBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboBootstrap.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
        //new SpringApplicationBuilder(DubboBootstrap.class)
        //        .run(args);
    }
}
