package com.csp.github.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csp.github.tenant.dto.TenantPermissionNode;
import com.csp.github.tenant.entity.TenantPermission;
import java.util.List;

/**
 * <p>
 * 后台用户权限表 服务类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
public interface ITenantPermissionService extends IService<TenantPermission> {

    /**
     * 以层级结构返回所有权限
     */
    List<TenantPermissionNode> treeList();

}
