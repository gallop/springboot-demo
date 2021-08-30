package com.gallop.file.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author gallop
 * date 2021-07-01 11:21
 * Description: 用来解决匿名用户访问无权限资源时的异常   ——  也就是未授权的问题
 * Modified By:
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = authException.getCause();
        authException.printStackTrace();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if (cause instanceof AccessDeniedException) {
                // 资源权限不足
                BaseResult resp = BaseResultUtil.build(401,"权限不足!",null);
                response.getWriter().write(mapper.writeValueAsString(resp));
            } else if (cause == null || cause instanceof AuthenticationException) {
                // 未带token或token无效
                // cause == null 一般可能是未带token
                BaseResult resp = BaseResultUtil.build(401,"未登录授权",null);
                response.getWriter().write(mapper.writeValueAsString(resp));
            }else {

                System.err.println("cause:"+cause.getClass().getName());
                BaseResult resp = BaseResultUtil.build(401,"未知权限问题",null);
                response.getWriter().write(mapper.writeValueAsString(resp));
            }
        } catch (IOException e) {
            log.error("其他异常error", e);
            throw new RuntimeException(e);
        }
    }
}
