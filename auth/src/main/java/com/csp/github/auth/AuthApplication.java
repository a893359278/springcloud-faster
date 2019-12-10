package com.csp.github.auth;

import com.csp.github.base.web.annotation.StarterApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * @author 陈少平
 * @date 2019-11-20 22:23
 */
@StarterApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
