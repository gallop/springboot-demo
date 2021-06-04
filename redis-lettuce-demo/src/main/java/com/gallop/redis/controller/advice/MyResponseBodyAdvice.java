package com.gallop.redis.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallop.redis.controller.RedisDemoController;
import com.gallop.redis.interceptor.RedisCacheInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * author gallop
 * date 2021-06-04 11:18
 * Description:
 * Modified By:
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.hasMethodAnnotation(PostMapping.class)) {
            return true;
        }

        //RequestMapping requestMapping = methodParameter.getMethodAnnotation(RequestMapping.class);
        //System.err.println("requestMapping.method()="+requestMapping.method());
        //methodParameter.hasMethodAnnotation(GetMapping.class)
        if (StringUtils.equals(methodParameter.getMethod().getName(),"getUserInfo") &&
                StringUtils.equals(RedisDemoController.class.getName(), methodParameter.getExecutable().getDeclaringClass().getName())) {
            return true;
        }

        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            String redisKey = RedisCacheInterceptor.createRedisKey(((ServletServerHttpRequest) serverHttpRequest).getServletRequest());
            /*String redisValue;
            if(body instanceof String){
                redisValue = (String)body;
            }else{
                redisValue = mapper.writeValueAsString(body);
            }*/
            this.redisTemplate.opsForValue().set(redisKey,body,Duration.ofHours(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}
