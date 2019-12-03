package com.csp.github.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2019-11-24 10:48
 */
@RestController
@StarterApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


}
