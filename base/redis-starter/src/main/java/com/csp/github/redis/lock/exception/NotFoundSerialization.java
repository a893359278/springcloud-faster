package com.csp.github.redis.lock.exception;

import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.BaseException;

/**
 * @author 陈少平
 * @date 2020-03-07 18:14
 */
public class NotFoundSerialization extends BaseException {

    public NotFoundSerialization() {
        this("Not found relation serialization, mark sure it exist");
    }

    public NotFoundSerialization(String msg) {
        super(DefaultResultType.SERVICE_EXCEPTION.getCode(), msg);
    }

    public NotFoundSerialization(String code, String msg) {
        super(code, msg);
    }
}
