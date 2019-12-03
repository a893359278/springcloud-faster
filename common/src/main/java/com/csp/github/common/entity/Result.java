package com.csp.github.common.entity;

import com.csp.github.common.exception.BaseException;
import com.csp.github.common.exception.ServiceException;
import com.csp.github.common.exception.ServiceSpecialException;

/**
 * 统一返回值
 * @author 陈少平
 * @date 2019-12-01 14:34
 */
public class Result {

    private String code;
    private Object data;
    private String msg;

    public Result(ResultType resultType) {
        this.code = resultType.getCode();
        this.msg = resultType.getMsg();
    }

    public Result(ResultType resultType, Object data) {
        this(resultType);
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

    public static Result ok(Object data) {
        return new Result(ResultType.OK, data);
    }

    public static Result fail(String code, String msg) {
        return new Result(code, new Object(), msg);
    }

    public static Result fail(String code, Object data ,String msg) {
        return new Result(code, new Object(), msg);
    }

    public static Result fail(ResultType resultType) {
        return new Result(resultType, new Object());
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
