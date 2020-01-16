package com.csp.github.base.common.entity;

/**
 * 定义默认的
 * @author 陈少平
 * @date 2019-12-03 22:08
 */
public enum DefaultResultType implements ResultType{

    OK("200", "ok"),
    SYS_ERROR("-1", "系统异常"),

    // 一般的，请求参数验证失败，应返回具体的错误信息。此处只定义状态码
    PARAM_INVALID("400", "请求参数验证失败"),

    METHOD_NOT_SUPPORT("405", "请求方法不允许"),

    SYS_BUSI("500", "系统繁忙,请稍后再试"),

    DUPLICATE_PRIMARY_KEY("10000", "唯一值冲突"),

    // 只定义统一的状态码
    SERVICE_EXCEPTION("20000", "业务异常"),


    FORMAT_EXCEPTION("30000","参数格式化异常"),


    ACCESS_DENIED("40001", "访问拒绝，没有权限"),
    AUTHENTICATION_FAIL("40002", "认证失败"),
    NEED_LOGIN("40003", "请登录"),
    USERNAME_PASSWORD_INCORRECT("40004", "账号或密码不正确"),

    TOKEN_NULL("50001", "token 不存在"),
    TOKEN_ILLEGAL("50002", "token 解析失败"),
    TOKEN_EXPIRATION("50003", "token 已过期"),
    TOKEN_FRESH("50004", "token 需要刷新"),

    EXIST("60001", "记录已存在"),
    NOT_EXIST("60002", "记录不存在");

    DefaultResultType(String code, String msg) {
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
