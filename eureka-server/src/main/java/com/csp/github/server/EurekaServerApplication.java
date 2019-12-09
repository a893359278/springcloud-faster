package com.csp.github.server;

import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2019-11-24 10:23
 */
@SpringBootApplication
@EnableEurekaServer
@RestController
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class);
    }

    @Resource
    private EurekaServerConfigBean eurekaServerConfigBean;

    @GetMapping("/enable/self-preservation")
    public void disableSelfPreservation() {
        eurekaServerConfigBean.setEnableSelfPreservation(false);
    }

    @GetMapping("/disable/self-preservation")
    public void enableSelfPreservation() {
        eurekaServerConfigBean.setEnableSelfPreservation(true);
    }
}
