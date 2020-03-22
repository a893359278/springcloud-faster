package com.csp.github.base.common.exception;

import com.csp.github.base.common.entity.DefaultResultType;

/**
 * @author 陈少平
 * @date 2020-03-07 08:42
 */
public class InternalException extends BaseException {
    public InternalException() {
    }

    public InternalException(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public InternalException(String code, String msg) {
        super(code, msg);
    }
}
