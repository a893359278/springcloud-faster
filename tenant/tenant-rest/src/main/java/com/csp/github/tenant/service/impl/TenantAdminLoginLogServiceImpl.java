package com.csp.github.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.tenant.entity.TenantAdminLoginLog;
import com.csp.github.tenant.mapper.TenantAdminLoginLogMapper;
import com.csp.github.tenant.service.ITenantAdminLoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户登录日志表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Service
public class TenantAdminLoginLogServiceImpl extends ServiceImpl<TenantAdminLoginLogMapper, TenantAdminLoginLog> implements ITenantAdminLoginLogService {

}
