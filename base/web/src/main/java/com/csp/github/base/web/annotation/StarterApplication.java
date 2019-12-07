package com.csp.github.base.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * springcloud 组合注解
 * @author 陈少平
 * @date 2019-11-16 15:18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.csp.github"})
public @interface StarterApplication {
}
