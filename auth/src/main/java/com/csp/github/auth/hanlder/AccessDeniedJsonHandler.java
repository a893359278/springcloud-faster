package com.csp.github.auth.hanlder;

import com.alibaba.fastjson.JSONObject;
import com.csp.github.auth.entity.AuthResultType;
import com.csp.github.common.entity.Result;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 没有权限处理器
 * @author 陈少平
 * @date 2019-12-01 16:25
 */
@Slf4j
public class AccessDeniedJsonHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.info("访问拒绝, 入参：{}", JSONObject.toJSONString(request.getParameterMap()));
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(Result.fail(AuthResultType.ACCESS_DENIED)));
    }
}
