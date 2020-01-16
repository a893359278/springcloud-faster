package com.csp.github.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.tenant.entity.TenantAdminRoleRelation;
import com.csp.github.tenant.mapper.TenantAdminRoleRelationMapper;
import com.csp.github.tenant.service.ITenantAdminRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和角色关系表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Service
public class TenantAdminRoleRelationServiceImpl extends ServiceImpl<TenantAdminRoleRelationMapper, TenantAdminRoleRelation> implements
        ITenantAdminRoleRelationService {

}
