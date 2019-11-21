package com.csp.github.auth.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2019-11-20 23:41
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String tt() {
        return "1";
    }
}
