package com.csp.github.auth.entity;


import com.csp.github.base.common.entity.ResultType;

/**
 *
 * @author 陈少平
 * @date 2019-12-04 20:04
 */
public enum AuthResultType implements ResultType {

    ACCESS_DENIED("40001", "访问拒绝，没有权限"),
    AUTHENTICATION_FAIL("40002", "认证失败"),
    NEED_LOGIN("40003", "请登录"),
    USERNAME_PASSWORD_INCORRECT("40004", "账号或密码不正确");

    private String code;
    private String msg;

    AuthResultType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
