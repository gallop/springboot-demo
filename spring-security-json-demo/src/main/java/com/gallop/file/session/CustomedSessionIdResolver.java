package com.gallop.file.session;

import com.gallop.file.constant.SessionConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * author gallop
 * date 2021-12-30 11:12
 * Description:
 * Modified By:
 */
@Data
public class CustomedSessionIdResolver implements HttpSessionIdResolver {

    private final String headerName;

    public CustomedSessionIdResolver(String headerName) {
        if (headerName == null) {
            throw new IllegalArgumentException("headerName cannot be null");
        }
        this.headerName = headerName;
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getHeader(this.headerName);
        if (StringUtils.isEmpty(headerValue)) {
            headerValue = (String) request.getAttribute(SessionConstants.SESSION_SEED);
            //headerValue = (String) request.getAttribute("token");
        }
        if (StringUtils.isEmpty(headerValue)) {
            headerValue = (String) request.getParameter(SessionConstants.SESSION_SEED);
        }
        //System.err.println("headerValue-request-url="+request.getRequestURL().toString());
        //System.err.println("headerValue-session-id="+headerValue);

        return (headerValue != null) ?
                Collections.singletonList(headerValue) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String sessionId) {
        httpServletResponse.setHeader(this.headerName, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader(this.headerName, "");
    }
}
