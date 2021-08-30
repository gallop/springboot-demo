package com.gallop.file.security.handler;

import com.alibaba.fastjson.JSON;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author gallop
 * date 2021-08-25 14:28
 * Description: 用来解决认证过的用户访问无权限资源时的异常  ——  也就是权限不足的问题
 * Modified By:
 */
@Component
public class MyAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        //httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        e.printStackTrace();
        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        BaseResult resp = BaseResultUtil.build(401,"没有访问权限!",null);
        httpServletResponse.getWriter().print(JSON.toJSONString(resp));
    }
}
