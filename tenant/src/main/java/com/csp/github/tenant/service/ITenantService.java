package com.csp.github.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csp.github.tenant.dto.TenantParam;
import com.csp.github.tenant.dto.UpdateAdminPasswordParam;
import com.csp.github.tenant.entity.Tenant;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRole;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 单元 服务类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
public interface ITenantService extends IService<Tenant> {

    /**
     * 根据用户名获取后台管理员
     */
    Tenant getAdminByUsername(String username);

    /**
     * 注册功能
     */
    @Transactional
    Tenant register(TenantParam umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
//    String login(String username, String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    Tenant getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    IPage<Tenant> list(String name, long pageNum, long pageSize);

    /**
     * 修改指定用户信息
     */
    int update(Long id, Tenant admin);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<TenantRole> getRoleList(Long adminId);

    /**
     * 修改用户的+-权限
     */
    @Transactional
    int updatePermission(List<Long> permissionIds);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<TenantPermission> getPermissionList(Long adminId);

    /**
     * 修改密码
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取当前用户登录信息
     * @return
     */
//    Tenant getAdminInfo();
//
//    Long getCurrentTenantId();
}
