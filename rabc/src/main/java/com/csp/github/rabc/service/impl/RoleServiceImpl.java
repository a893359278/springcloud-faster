package com.csp.github.rabc.service.impl;

import com.csp.github.rabc.entity.Role;
import com.csp.github.rabc.mapper.RoleMapper;
import com.csp.github.rabc.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
