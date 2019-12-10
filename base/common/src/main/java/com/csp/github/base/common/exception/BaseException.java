package com.csp.github.base.common.exception;

import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.entity.ResultType;

/**
 * @author 陈少平
 * @date 2019-12-03 22:01
 */
public class BaseException extends RuntimeException{

    private String code;
    private String msg;

    public BaseException() {
        super(DefaultResultType.SYS_BUSI.getMsg());
        this.code = DefaultResultType.SYS_BUSI.getCode();
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(ResultType defaultResultType) {
        super(defaultResultType.getMsg());
        this.code = defaultResultType.getCode();
        this.msg = defaultResultType.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
