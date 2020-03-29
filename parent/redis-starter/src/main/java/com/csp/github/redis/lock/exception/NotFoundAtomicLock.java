package com.csp.github.redis.lock.exception;

import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.BaseException;

/**
 * @author 陈少平
 * @date 2020-03-07 08:09
 */
public class NotFoundAtomicLock extends BaseException {
    public NotFoundAtomicLock() {
        this("Not found relation atomicLock, mark sure it exist");
    }

    public NotFoundAtomicLock(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public NotFoundAtomicLock(String code, String msg) {
        super(code, msg);
    }


}
