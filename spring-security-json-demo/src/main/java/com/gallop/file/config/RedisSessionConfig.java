package com.gallop.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * author gallop
 * date 2021-08-25 11:08
 * Description: 开启spring session redis缓存
 * Modified By:
 */
@Configuration
//设置session失效时间为30分钟
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class RedisSessionConfig {

     /**
      * @date 2021-08-25 11:12
      * Description: 如果不设置这个bean spring默认的是 CookieHttpSessionIdResolver,
      * cookie 保存的是经过base64 的sessionId，
      * 如果是Header 的方式，则是直接在header 保存指定名字的sessionId (未经base64)
      * Param:
      * return:
      **/
    @Bean
    public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
        //return new CookieHttpSessionIdResolver();
        return new HeaderHttpSessionIdResolver("x-auth-token");
    }
}
