package com.csp.github.resource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源搜集器
 * @author 陈少平
 * @date 2019-11-15 22:23
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceCollection {

    // 资源 名称
    String name() default "";
    // 版本号，仅用于收集权限时，进行更新操作
    int version() default 1;
    // 资源描述
    String description() default "";
    // 额外的数据
    String extra() default "";
}
