package com.gallop.file.security.strategy;

import com.alibaba.fastjson.JSON;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author gallop
 * date 2021-08-25 15:55
 * Description: 配置 session 无效时的处理策略
 * Modified By:
 */
@Component
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        //httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        BaseResult resp = BaseResultUtil.build(402,"无效的session！",null);
        httpServletResponse.getWriter().write(JSON.toJSONString(resp));
    }
}
