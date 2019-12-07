package com.csp.github.base.common.entity;

/**
 * 抽象的返回值类型，用于其他服务定义异常
 * @author 陈少平
 * @date 2019-12-04 20:02
 */
public interface ResultType {
    String getCode();
    String getMsg();
}
