package com.csp.github.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.tenant.entity.TenantRolePermissionRelation;
import com.csp.github.tenant.mapper.TenantRolePermissionRelationMapper;
import com.csp.github.tenant.service.ITenantRolePermissionRelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色和权限关系表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Service
public class TenantRolePermissionRelationServiceImpl extends ServiceImpl<TenantRolePermissionRelationMapper, TenantRolePermissionRelation> implements
        ITenantRolePermissionRelationService {

}
