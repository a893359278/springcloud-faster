package com.csp.github.redis.lock;

/**
 * 锁的类型
 * @author 陈少平
 * @date 2020-03-05 19:50
 */
public enum LockType {

    REDIS,
    ZOOKEEPER,
    LOCAL
}
