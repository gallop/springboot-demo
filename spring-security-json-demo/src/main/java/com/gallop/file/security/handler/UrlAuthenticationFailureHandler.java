package com.gallop.file.security.handler;

import com.alibaba.fastjson.JSON;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author gallop
 * date 2021-08-24 16:53
 * Description: 登录失败的handler
 * Modified By:
 */
@Component
public class UrlAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(401);
        e.printStackTrace();
        PrintWriter writer = httpServletResponse.getWriter();
        BaseResult resp = BaseResultUtil.build(401,"登陆失败",null);
        if (e instanceof BadCredentialsException) {
            resp.setMsg("用户名或密码错误，请检查");
        }else if(e instanceof LockedException){
            resp.setMsg("账户被锁定，请联系管理员");
        }else if(e instanceof CredentialsExpiredException){
            resp.setMsg("密码已过期，请联系管理员");
        }else if(e instanceof AccountExpiredException){
            resp.setMsg("账户已过期，请联系管理员");
        }else if(e instanceof DisabledException){
            resp.setMsg("账户被禁用，请联系管理员");
        }
        writer.write(JSON.toJSONString(resp));
        writer.flush();
        writer.close();
    }
}
