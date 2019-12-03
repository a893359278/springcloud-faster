package com.csp.github.common.entity;

/**
 * 定义默认的
 * @author 陈少平
 * @date 2019-12-03 22:08
 */
public enum ResultType {

    OK("200", "ok"),
    SYS_ERROR("-1", "系统异常"),

    // 一般的，请求参数验证失败，应返回具体的错误信息。此处只定义状态码
    PARAM_INVALID("400", "请求参数验证失败"),

    METHOD_NOT_SUPPORT("405", "请求方法不允许"),

    SYS_BUSI("500", "系统繁忙,请稍后再试"),

    DUPLICATE_PRIMARY_KEY("10000", "唯一值冲突"),

    // 只定义统一的状态码
    SERVICE_EXCEPTION("20000", "业务异常"),


    FORMAT_EXCEPTION("30000","参数格式化异常");

    ResultType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

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
