package com.csp.github.rabc.service;

import com.csp.github.rabc.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
public interface IAdminUserService extends IService<AdminUser> {

    /**
     * 获取用户信息，以及角色，权限
     * @param id
     * @return
     */
    AdminUser getUserInfoWithRolesAndPermissions(Long id);

    /**
     * 用户注册
     * @param user
     * @return
     */
    AdminUser register(AdminUser user);
}
