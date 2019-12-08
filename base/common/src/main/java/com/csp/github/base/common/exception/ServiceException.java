package com.csp.github.base.common.exception;

import com.csp.github.base.common.entity.DefaultResultType;

/**
 * @author 陈少平
 * @date 2019-12-03 22:02
 */
public class ServiceException extends BaseException{

    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public ServiceException(String code, String msg) {
        super(code, msg);
    }

    public ServiceException(DefaultResultType defaultResultType) {
        super(defaultResultType);
    }
}
