package com.csp.github.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.tenant.entity.TenantAdminPermissionRelation;
import com.csp.github.tenant.mapper.TenantAdminPermissionRelationMapper;
import com.csp.github.tenant.service.ITenantAdminPermissionRelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Service
public class TenantAdminPermissionRelationServiceImpl extends
        ServiceImpl<TenantAdminPermissionRelationMapper, TenantAdminPermissionRelation> implements
        ITenantAdminPermissionRelationService {

}
