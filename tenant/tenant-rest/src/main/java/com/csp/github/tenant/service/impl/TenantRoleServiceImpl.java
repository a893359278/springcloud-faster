package com.csp.github.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRole;
import com.csp.github.tenant.entity.TenantRolePermissionRelation;
import com.csp.github.tenant.mapper.TenantRoleMapper;
import com.csp.github.tenant.mapper.TenantRolePermissionRelationMapper;
import com.csp.github.tenant.service.ITenantRoleService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Service
public class TenantRoleServiceImpl extends ServiceImpl<TenantRoleMapper, TenantRole> implements ITenantRoleService {


    @Resource
    private TenantRolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<TenantPermission> getPermissionList(Long roleId) {
        return rolePermissionRelationMapper.getPermissionList(roleId);
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        QueryWrapper<TenantRolePermissionRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TenantRolePermissionRelation::getRoleId, roleId);
        rolePermissionRelationMapper.delete(wrapper);
        //批量插入新关系
        List<TenantRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            TenantRolePermissionRelation relation = new TenantRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationMapper.insertList(relationList);
    }

}
