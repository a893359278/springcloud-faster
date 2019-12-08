package com.csp.github.rabc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csp.github.base.common.constants.Constants;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.base.common.security.BCrypt;
import com.csp.github.base.common.security.BCryptPasswordEncoder;
import com.csp.github.base.common.utils.GeneratorUtils;
import com.csp.github.rabc.entity.AdminUser;
import com.csp.github.rabc.mapper.AdminUserMapper;
import com.csp.github.rabc.service.IAdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    @Resource
    AdminUserMapper userMapper;

    @Override
    public AdminUser getUserInfoWithRolesAndPermissions(Long id) {
        return userMapper.getUserInfoWithRolesAndPermissions(id);
    }

    @Override
    public AdminUser register(AdminUser user) {

        LambdaQueryWrapper<AdminUser> wrapper = new QueryWrapper<AdminUser>().lambda().eq(AdminUser::getUsername, user.getUsername());
        AdminUser tmp = getOne(wrapper);
        if (Objects.nonNull(tmp)) {
            throw new ServiceException("用户已存在！");
        }

        String salt = BCrypt.gensalt();
        AdminUser adminUser = user.setSalt(salt)
                .setPassword(new BCryptPasswordEncoder().encode(user.getPassword() + salt))
                .setAccountNonExpired(false)
                .setAccountNonLocked(false)
                .setCreatedTime(LocalDateTime.now())
                .setCredentialsNonExpired(false)
                .setDeleted(Constants.NO)
                .setEnabled(true)
                .setUpdatedTime(LocalDateTime.now());
        save(adminUser);
        return adminUser;
    }


}
