package com.gallop.redis.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.gallop.redis.vo.User;
import org.springframework.stereotype.Component;

/**
 * author gallop
 * date 2021-06-07 9:07
 * Description: redis 消息订阅发布：消息接收
 * Modified By:
 */
@Component
public class RedisReceiver {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
            .activateDefaultTyping(LaissezFaireSubTypeValidator.instance , ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

    public void receiveMessage(String message) throws Exception {
        System.err.println("message:"+message);
        User user = objectMapper.readValue(message, User.class);
        System.err.println("user:"+user.toString());
    }
}
