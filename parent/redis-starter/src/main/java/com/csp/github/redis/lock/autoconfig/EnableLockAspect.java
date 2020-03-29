package com.csp.github.redis.lock.autoconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 是否启用 lockAspect
 * @author 陈少平
 * @date 2020-03-07 08:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LockAutoConfiguration.class)
public @interface EnableLockAspect {
}
