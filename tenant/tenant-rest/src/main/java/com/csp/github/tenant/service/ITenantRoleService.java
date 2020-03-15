package com.csp.github.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRole;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
public interface ITenantRoleService extends IService<TenantRole> {

    List<TenantPermission> getPermissionList(Long roleId);

    int updatePermission(Long roleId, List<Long> permissionIds);
}
