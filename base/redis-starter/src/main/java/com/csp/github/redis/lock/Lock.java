package com.csp.github.redis.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * lock
 * @author 陈少平
 * @date 2020-03-05 19:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    /**
     * 锁类型
     * @return
     */
    LockType type() default LockType.REDIS;

    /**
     * 序列化方式
     * @return
     */
    SerializationType serialization() default SerializationType.PROTOBUF;

    /**
     * 锁时间
     * @return
     */
    long time() default 3;

    /**
     * 锁时间的单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 获取锁失败，最多尝试次数
     * @return
     */
    int failTryCount() default 3;

    /**
     * 失败尝试间隔时间
     * @return
     */
    long failInterval() default 1000;

    /**
     * 失败尝试间隔时间单位
     * @return
     */
    TimeUnit failIntervalTimeUnit() default TimeUnit.MILLISECONDS;
}
