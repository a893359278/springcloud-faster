package com.csp.github.resource.send;

/**
 * @author 陈少平
 * @date 2020-01-15 22:11
 */
public interface Sender {

    /**
     * 发送收集到的权限
     * @param msg
     */
    void sendPermissions(String msg);
}
