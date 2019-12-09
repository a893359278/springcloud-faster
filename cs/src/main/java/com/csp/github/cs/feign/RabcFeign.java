package com.csp.github.cs.feign;

import com.csp.github.cs.entity.AdminUser;
import org.springframework.cloud.openfeign.DefaultHystrixFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 陈少平
 * @date 2019-12-09 20:07
 */
@FeignClient(value = "rabc-service", path = "/adminUser", fallbackFactory = DefaultHystrixFallbackFactory.class)
public interface RabcFeign {
    @GetMapping("/{id}")
    AdminUser userFullInfo(@PathVariable Long id);

    @GetMapping("/tt")
    String tt();
}
