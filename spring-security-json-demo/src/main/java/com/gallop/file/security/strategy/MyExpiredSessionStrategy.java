package com.gallop.file.security.strategy;

import com.alibaba.fastjson.JSON;
import com.gallop.file.base.BaseResult;
import com.gallop.file.base.BaseResultUtil;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * author gallop
 * date 2021-08-25 16:00
 * Description: 配置session过期处理策略
 * Modified By:
 */
@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //event.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
        event.getResponse().setContentType("application/json;charset=UTF-8");
        BaseResult resp = BaseResultUtil.build(402,"session已经过期！",null);
        event.getResponse().getWriter().write(JSON.toJSONString(resp));
    }
}
