package com.csp.github.tenant.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csp.github.tenant.entity.TenantAdminRoleRelation;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
public interface TenantAdminRoleRelationMapper extends BaseMapper<TenantAdminRoleRelation> {
    /**
     * 批量插入用户角色关系
     */
    @SqlParser(filter = true)
    int insertList(@Param("list") List<TenantAdminRoleRelation> adminRoleRelationList);

    /**
     * 获取用于所有角色
     */
    List<TenantRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 获取用户所有角色权限
     */
    List<TenantPermission> getRolePermissionList(@Param("adminId") Long adminId);

    /**
     * 获取用户所有权限(包括+-权限)
     */
    @SqlParser(filter = true)
    List<TenantPermission> getPermissionList(@Param("tenantId") Long tenantId);

}
