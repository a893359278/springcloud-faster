package com.csp.github.auth;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 陈少平
 * @date 2019-11-20 22:23
 */
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
