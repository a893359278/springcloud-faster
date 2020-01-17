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
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        // spring security 会将异常放在 该属性中，获取异常，如果是我们自己的异常信息，直接抛出即可
        Throwable ex = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (ex instanceof BaseException) {
            BaseException exception = (BaseException) ex;
            writer.write(JSONObject.toJSONString(Result.fail(exception)));
        } else {
            writer.write(JSONObject.toJSONString(Result.fail(DefaultResultType.ACCESS_DENIED)));
        }
    }
}
