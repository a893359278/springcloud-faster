package com.csp.github.redis.lock.exception;

import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.entity.ResultType;
import com.csp.github.base.common.exception.BaseException;

/**
 * 获取锁失败时，抛出此异常
 * @author 陈少平
 * @date 2020-03-05 21:46
 */
public class TryLockFailException extends BaseException {

    public TryLockFailException() {
        this("Internet train, please wait a moment!");
    }

    public TryLockFailException(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public TryLockFailException(String code, String msg) {
        super(code, msg);
    }

    public TryLockFailException(ResultType defaultResultType) {
        super(defaultResultType);
    }


}
