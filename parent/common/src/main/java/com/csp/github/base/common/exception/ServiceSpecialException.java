package com.csp.github.base.common.exception;

import com.csp.github.base.common.entity.DefaultResultType;

/**
 * data 参数有值的异常。比较特别，但是确实部分特殊场景会需要。
 * @author 陈少平
 * @date 2019-12-03 22:44
 */
public class ServiceSpecialException extends BaseException {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ServiceSpecialException(Object data) {
        super();
        this.data = data;
    }

    public ServiceSpecialException(String code, String msg, Object data) {
        super(code, msg);
        this.data = data;
    }

    public ServiceSpecialException(DefaultResultType defaultResultType, Object data) {
        super(defaultResultType);
        this.data = data;
    }
}
