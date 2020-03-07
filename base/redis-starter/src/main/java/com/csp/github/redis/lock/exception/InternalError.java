package com.csp.github.redis.lock.exception;

import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.BaseException;

/**
 * @author 陈少平
 * @date 2020-03-07 08:42
 */
public class InternalError extends BaseException {
    public InternalError() {
    }

    public InternalError(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public InternalError(String code, String msg) {
        super(code, msg);
    }
}
