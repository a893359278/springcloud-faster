package com.csp.github.auth.exception;

import com.csp.github.base.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 陈少平
 * @date 2020-01-01 10:08
 */
@Slf4j
@RestControllerAdvice
public class TokenExceptionAdvice {

    @ExceptionHandler(value = {TokenNeedFreshException.class})
    public Result tokenNeedFreshException(TokenNeedFreshException ex) {
        log.info("token 刷新");
        return Result.fail(ex);
    }
}
