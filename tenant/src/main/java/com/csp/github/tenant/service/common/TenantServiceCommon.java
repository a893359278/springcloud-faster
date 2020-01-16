package com.csp.github.tenant.service.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csp.github.tenant.entity.Tenant;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.mapper.TenantAdminRoleRelationMapper;
import com.csp.github.tenant.mapper.TenantMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author 陈少平
 * @date 2019-12-27 19:16
 */
@Component
public class TenantServiceCommon {

    @Resource
    TenantMapper tenantMapper;
    @Resource
    TenantAdminRoleRelationMapper adminRoleRelationMapper;


    public Tenant getAdminByUsername(String username) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Tenant::getUsername, username);
        return tenantMapper.selectOne(wrapper);
    }

    public List<TenantPermission> getPermissionList(Long unitId) {
        return adminRoleRelationMapper.getPermissionList(unitId);
    }

}
