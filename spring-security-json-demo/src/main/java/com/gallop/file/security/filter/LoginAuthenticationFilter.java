package com.gallop.file.security.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallop.file.security.bean.LoginBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * author gallop
 * date 2021-08-24 11:44
 * Description: 重写filter以支持用户登录以json 格式提交
 * Modified By:
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        //如果是application/json类型，做如下处理
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){

            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()){
                LoginBean loginBean = mapper.readValue(is, LoginBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        loginBean.getUserName(), loginBean.getPassword());
                log.info("authenticationBean={}", JSON.toJSONString(loginBean));
            }catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            //transmit it to UsernamePasswordAuthenticationFilter
            return super.attemptAuthentication(request, response);
        }
    }
}
