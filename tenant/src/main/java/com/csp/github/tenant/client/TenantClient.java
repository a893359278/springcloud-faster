package com.csp.github.tenant.client;

/**
 * @author 陈少平
 * @date 2020-01-16 22:54
 */

import com.csp.github.tenant.entity.Tenant;
import com.csp.github.tenant.entity.TenantPermission;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.DefaultHystrixFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "tenant-service", path = "/tenant", fallbackFactory = DefaultHystrixFallbackFactory.class)
public interface TenantClient {

    @GetMapping("/tenant/username")
    Tenant getTenantByUsername(@NotNull String username);

    @GetMapping(value = "/permission")
    List<TenantPermission> getPermissionList(@NotNull Long tenantId);
}
