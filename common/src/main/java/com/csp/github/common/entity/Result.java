package com.csp.github.common.entity;

/**
 * 统一返回值
 * @author 陈少平
 * @date 2019-12-01 14:34
 */
public class Result<T> {
    private String code;
    private T data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
