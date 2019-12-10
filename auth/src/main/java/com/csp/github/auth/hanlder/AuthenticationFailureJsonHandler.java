package com.csp.github.auth.hanlder;

import com.alibaba.fastjson.JSONObject;
import com.csp.github.auth.entity.AuthResultType;
import com.csp.github.base.common.entity.Result;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 登录失败处理器
 * @author 陈少平
 * @date 2019-12-01 16:24
 */
@Slf4j
public class AuthenticationFailureJsonHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        log.info("认证失败, 入参：{}", JSONObject.toJSONString(request.getParameterMap()));
        PrintWriter writer = response.getWriter();
        String s = JSONObject.toJSONString(Result.fail(AuthResultType.AUTHENTICATION_FAIL));
        writer.write(s);
    }
}
