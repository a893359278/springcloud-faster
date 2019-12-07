package com.csp.github.feign;

import org.springframework.cloud.openfeign.DefaultHystrixFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 陈少平
 * @date 2019-12-06 23:36
 */
@FeignClient(value = "rabc-service", path = "/adminUser", fallbackFactory = DefaultHystrixFallbackFactory.class)
public interface IRabc {


    @GetMapping("/{id}")
    public Object userFullInfo(@PathVariable Long id);

    @GetMapping("/zzzz")
    public String s();
}
