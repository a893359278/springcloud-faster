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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 未登录处理器
 * @author 陈少平
 * @date 2019-12-01 16:24
 */
@Slf4j
public class AuthenticationJsonEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.info("用户还未登录, 入参：{}", JSONObject.toJSONString(request.getParameterMap()));
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(Result.fail(AuthResultType.NEED_LOGIN)));
    }
}
