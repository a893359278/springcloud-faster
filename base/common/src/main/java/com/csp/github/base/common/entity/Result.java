package com.csp.github.base.common.entity;

import com.csp.github.base.common.exception.BaseException;
import com.csp.github.base.common.exception.ServiceSpecialException;

/**
 * 统一返回值
 * @author 陈少平
 * @date 2019-12-01 14:34
 */
public class Result {

    private String code;
    private Object data;
    private String msg;

    public Result(ResultType ResultType) {
        this.code = ResultType.getCode();
        this.msg = ResultType.getMsg();
    }

    public Result(ResultType ResultType, Object data) {
        this(ResultType);
        this.data = data;
    }

    public Result(String code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result ok() {
        return ok(new Object());
    }

    public static Result ok(Object data) {
        return new Result(DefaultResultType.OK, data);
    }

    public static Result fail() {
        return new Result(DefaultResultType.SYS_BUSI.getCode(), DefaultResultType.SYS_BUSI.getMsg());
    }

    public static Result fail(String code, String msg) {
        return new Result(code, new Object(), msg);
    }

    public static Result fail(String code, Object data ,String msg) {
        return new Result(code, new Object(), msg);
    }

    public static Result fail(ResultType ResultType) {
        return new Result(ResultType, new Object());
    }

    public static Result fail (BaseException ex) {
        return new Result(ex.getCode(), new Object(), ex.getMsg());
    }

    public static Result fail(ServiceSpecialException ex) {
        return new Result(ex.getCode(), ex.getData(), ex.getMsg());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
