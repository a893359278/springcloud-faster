package com.csp.github.rabc.client;

import com.csp.github.rabc.entity.AdminUser;
import org.springframework.cloud.openfeign.DefaultHystrixFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 陈少平
 * @date 2019-12-10 21:34
 */
@FeignClient(value = "rabc-service", path = "/adminUser", fallbackFactory = DefaultHystrixFallbackFactory.class)
public interface AdminUserClient {

    /**
     * 获取用户信息，包括权限，角色
     * @param id 用户ID
     */
    @GetMapping("/{id}")
    AdminUser userFullInfo(@PathVariable Long id);

    /**
     * 获取用户信息，包括权限，角色
     */
    @GetMapping("/info")
    AdminUser getUserFullInfoByUsername(@RequestParam String username);

    /**
     * 通过用户名获取用户
     */
    @GetMapping
    AdminUser getUser(@RequestParam String username);
}


