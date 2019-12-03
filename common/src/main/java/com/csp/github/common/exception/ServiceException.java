package com.csp.github.common.exception;

import com.csp.github.common.entity.ResultType;

/**
 * @author 陈少平
 * @date 2019-12-03 22:02
 */
public class ServiceException extends BaseException{

    public ServiceException() {
    }

    public ServiceException(String code, String msg) {
        super(code, msg);
    }

    public ServiceException(ResultType resultType) {
        super(resultType);
    }
}
