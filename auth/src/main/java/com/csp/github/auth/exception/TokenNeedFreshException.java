package com.csp.github.auth.exception;


import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.entity.ResultType;
import com.csp.github.base.common.exception.BaseException;

/**
 * @author 陈少平
 * @date 2020-01-01 09:32
 */
public class TokenNeedFreshException extends BaseException {
    public TokenNeedFreshException() {
    }

    public TokenNeedFreshException(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public TokenNeedFreshException(String code, String msg) {
        super(code, msg);
    }

    public TokenNeedFreshException(ResultType defaultResultType) {
        super(defaultResultType);
    }

}
