package com.csp.github.redis.lock;

import java.util.function.Supplier;

/**
 * @author 陈少平
 * @date 2020-03-05 20:55
 */
public interface AtomicLock {

    /**
     * 支持的锁方式
     * @param lockType
     * @return
     */
    boolean support(LockType lockType);

    /**
     * 尝试获取锁，根据 lock 元注解信息，获取锁失败之后会进行尝试。
     * @param k
     * @param v
     * @param lock
     * @param supplier 执行锁成功之后的回调
     * @return 返回 supplier 的执行结果
     */
    Object tryLock(String k, Object v, LockProperty lock, Supplier supplier);

    /**
     * 解锁
     * @param k
     * @param v
     * @param serializationType
     * @return
     */
    boolean unLock(String k, Object v, SerializationType serializationType);
}
