package com.csp.github.rabc.provider;

import com.csp.github.rabc.entity.AdminUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 陈少平
 * @date 2019-12-06 08:20
 */
@FeignClient(name = "rabc-service", path = "/adminUser", fallback = AdminUserProviderFallback.class)
public interface AdminUserProvider {

    @GetMapping("/{id}")
    AdminUser userFullInfo(@PathVariable Long id);
}
