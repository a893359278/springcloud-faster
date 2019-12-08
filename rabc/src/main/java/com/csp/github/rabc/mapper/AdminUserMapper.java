package com.csp.github.rabc.mapper;

import com.csp.github.rabc.entity.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    /**
     * 获取用户信息以及角色，权限
     * @param id
     * @return
     */
    AdminUser getUserInfoWithRolesAndPermissions(Long id);
}
