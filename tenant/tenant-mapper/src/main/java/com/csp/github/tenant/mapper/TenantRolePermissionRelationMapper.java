package com.csp.github.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRolePermissionRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户角色和权限关系表 Mapper 接口
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
public interface TenantRolePermissionRelationMapper extends BaseMapper<TenantRolePermissionRelation> {

    /**
     * 根据角色获取权限
     */
    List<TenantPermission> getPermissionList(@Param("roleId") Long roleId);

    /**
     * 批量插入角色和权限关系
     */
    int insertList(@Param("list") List<TenantRolePermissionRelation> list);

}
