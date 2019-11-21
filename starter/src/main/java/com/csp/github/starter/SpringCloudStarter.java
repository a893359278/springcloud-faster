package com.csp.github.starter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * springcloud 组合注解
 * @author 陈少平
 * @date 2019-11-16 15:18
 */
@SpringBootApplication
@EnableDiscoveryClient
public @interface SpringCloudStarter {
}
