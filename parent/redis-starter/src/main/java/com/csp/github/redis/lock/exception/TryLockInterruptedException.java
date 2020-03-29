package com.csp.github.redis.lock.exception;

import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.entity.ResultType;
import com.csp.github.base.common.exception.BaseException;

/**
 * 获取锁过程中，因为线程睡眠而导致的中断异常
 * @author 陈少平
 * @date 2020-03-05 21:48
 */
public class TryLockInterruptedException extends BaseException {

    public TryLockInterruptedException() {
        this("Thread of lock had sleep, but it was interrupted. stopping your stupid behavior!");
    }

    public TryLockInterruptedException(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public TryLockInterruptedException(String code, String msg) {
        super(code, msg);
    }

    public TryLockInterruptedException(ResultType defaultResultType) {
        super(defaultResultType);
    }

}
