package com.csp.github;

import com.csp.github.base.web.annotation.StarterApplication;
import com.csp.github.feign.IRabc;
import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2019-12-06 23:34
 */
@RestController
@StarterApplication
public class App {

    @Resource
    IRabc rabc;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @GetMapping("/zxv")
    public String dd() {
        return rabc.s();
    }

    @GetMapping("/cx")
    public Object dz() {
        Object o = rabc.userFullInfo(21L);
        return o;
    }
}
