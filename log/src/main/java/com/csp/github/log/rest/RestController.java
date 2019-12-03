package com.csp.github.log.rest;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陈少平
 * @date 2019-11-29 19:49
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping
    public String test() {
        System.out.println("111");
        return "1111";
    }
}
