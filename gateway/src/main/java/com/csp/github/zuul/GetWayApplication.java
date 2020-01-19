package com.csp.github.zuul;

import com.csp.github.base.web.annotation.StarterApplication;
import com.csp.github.swagger.autoconfig.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author 陈少平
 * @date 2019-12-01 18:45
 */
@EnableSwagger
@StarterApplication
@EnableZuulProxy
public class GetWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetWayApplication.class);
    }
}
