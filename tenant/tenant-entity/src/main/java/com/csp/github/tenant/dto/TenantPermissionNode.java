package com.csp.github.tenant.dto;

import com.csp.github.tenant.entity.TenantPermission;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by macro on 2018/9/30.
 */
public class TenantPermissionNode extends TenantPermission {
    @Getter
    @Setter
    private List<TenantPermissionNode> children;
}
