package com.gallop.file.security.handler;

import com.alibaba.fastjson.JSON;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author gallop
 * date 2021-08-25 14:45
 * Description:
 * Modified By:
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        BaseResult resp = BaseResultUtil.success("注销退出成功");
        writer.write(JSON.toJSONString(resp));
        writer.flush();
        writer.close();
    }
}
