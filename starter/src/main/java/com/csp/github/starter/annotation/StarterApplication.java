package com.csp.github.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * springcloud 组合注解
 * @author 陈少平
 * @date 2019-11-16 15:18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.csp.github"})
public @interface StarterApplication {
}
