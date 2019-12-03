package com.csp.github.common.exception;

import com.csp.github.common.entity.Result;
import com.csp.github.common.entity.ResultType;

/**
 * @author 陈少平
 * @date 2019-12-03 22:01
 */
public class BaseException extends RuntimeException{

    private String code;
    private String msg;

    public BaseException() {
        super(ResultType.SYS_BUSI.getMsg());
        this.code = ResultType.SYS_BUSI.getCode();
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(ResultType resultType) {
        super(resultType.getMsg());
        this.code = resultType.getCode();
        this.msg = resultType.getMsg();
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
