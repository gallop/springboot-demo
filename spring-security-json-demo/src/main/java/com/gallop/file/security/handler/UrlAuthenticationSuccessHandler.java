package com.gallop.file.security.handler;

import com.alibaba.fastjson.JSON;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import com.gallop.file.security.bean.SysLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * author gallop
 * date 2021-08-24 16:56
 * Description: 登入成功的handler
 * Modified By:
 */
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //Subject currentUser = (Subject) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysLoginUser loginUser = (SysLoginUser)authentication.getPrincipal();
        String token = httpServletRequest.getSession().getId();
        String tokenStr = Base64.getEncoder().encodeToString(token.getBytes());
        System.err.println("---sessionId-base64-encoder="+tokenStr);
        System.err.println("---sessionId="+token);
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        Cookie cookie = new Cookie("token",token);
        //httpServletResponse.addCookie(cookie);
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setStatus(200);
        PrintWriter writer = httpServletResponse.getWriter();
        BaseResult resp = BaseResultUtil.success("登录成功",result);
        writer.write(JSON.toJSONString(resp));
        writer.flush();
        writer.close();
    }
}
