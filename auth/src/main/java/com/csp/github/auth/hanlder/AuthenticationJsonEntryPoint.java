package com.csp.github.auth.hanlder;

import com.alibaba.fastjson.JSONObject;
import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.entity.Result;
import com.csp.github.base.common.exception.BaseException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
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
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Throwable ex = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (ex instanceof BaseException) {
            BaseException exception = (BaseException) ex;
            writer.write(JSONObject.toJSONString(Result.fail(exception)));
        } else {
            writer.write(JSONObject.toJSONString(Result.fail(DefaultResultType.NEED_LOGIN)));
        }
    }
}
