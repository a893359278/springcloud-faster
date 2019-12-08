package com.csp.github.rabc.service.impl;

import com.csp.github.rabc.entity.Permission;
import com.csp.github.rabc.mapper.PermissionMapper;
import com.csp.github.rabc.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
