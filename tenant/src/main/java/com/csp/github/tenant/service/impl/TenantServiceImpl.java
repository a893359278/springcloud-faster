package com.csp.github.tenant.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.base.common.constants.Constants;
import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.base.common.utils.BCryptUtils;
import com.csp.github.redis.token.TokenStore;
import com.csp.github.tenant.dto.TenantParam;
import com.csp.github.tenant.dto.UpdateAdminPasswordParam;
import com.csp.github.tenant.entity.Tenant;
import com.csp.github.tenant.entity.TenantAdminLoginLog;
import com.csp.github.tenant.entity.TenantAdminPermissionRelation;
import com.csp.github.tenant.entity.TenantAdminRoleRelation;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRole;
import com.csp.github.tenant.mapper.TenantAdminLoginLogMapper;
import com.csp.github.tenant.mapper.TenantAdminPermissionRelationMapper;
import com.csp.github.tenant.mapper.TenantAdminRoleRelationMapper;
import com.csp.github.tenant.mapper.TenantMapper;
import com.csp.github.tenant.service.ITenantService;
import com.csp.github.tenant.service.common.TenantServiceCommon;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * 单元 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Slf4j
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Resource
    TenantServiceCommon tenantServiceCommon;
    @Resource
    TenantMapper tenantMapper;
    @Resource
    TenantAdminRoleRelationMapper adminRoleRelationMapper;
    @Resource
    TenantAdminPermissionRelationMapper adminPermissionRelationMapper;
    @Resource
    TenantAdminLoginLogMapper loginLogMapper;
    @Resource
    TokenStore tokenStore;

    @Override
    public Tenant getAdminByUsername(String username) {
        return tenantServiceCommon.getTenantByUsername(username);
    }

    @Override
    public Tenant register(TenantParam umsAdminParam) {

        // 判断是否存在
        Tenant tmp = this.getAdminByUsername(umsAdminParam.getUsername());
        if (Objects.nonNull(tmp)) {
            throw new ServiceException(DefaultResultType.EXIST);
        }

        Tenant umsAdmin = new Tenant();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(LocalDateTime.now());
        //查询是否有相同用户名的用户
        //将密码进行加密操作
        String encodePassword = BCryptUtils.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        tenantMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        Tenant tenant = tenantServiceCommon.getTenantByUsername(username);
        if (Objects.nonNull(tenant)) {
            if (BCryptUtils.matches(password, tenant.getPassword())) {
                checkTenantStatus(tenant);
                List<TenantPermission> permissionList = tenantServiceCommon.getPermissionList(tenant.getId());

                String token = tokenStore.generatorUUIDToken();
                tokenStore.saveTenantInfo(tenant.getId(), token, permissionList, tenant);
                return token;
            } else {
                throw new ServiceException(DefaultResultType.USERNAME_PASSWORD_INCORRECT);
            }
        } else {
            throw new ServiceException("账号不存在");
        }
    }

    private void checkTenantStatus(Tenant tenant) {
        if (tenant.getEnable() == Constants.NO) {
            throw new ServiceException("账号异常！");
        }
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        Tenant admin = getAdminByUsername(username);
        TenantAdminLoginLog loginLog = new TenantAdminLoginLog();
        loginLog.setTenantId(admin.getId());
        loginLog.setCreateTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }


    @Override
    public String refreshToken(String oldToken) {
        String token = "23213123";
        if (StringUtils.isEmpty(token)) {
            log.info("token 刷新失败！");
            throw new ServiceException("token 刷新失败");
        }
        return token;
    }

    @Override
    public Tenant getItem(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public IPage<Tenant> list(String name, long pageNum, long pageSize) {
        Page<Tenant> page = new Page<>();
        LambdaQueryWrapper<Tenant> wrapper = new QueryWrapper<Tenant>().lambda();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Tenant::getUsername, "%" + name + "%");
            wrapper.or(true).like(Tenant::getNickName, "%" + name + "%");
        }
        return tenantMapper.selectPage(page, wrapper);
    }

    @Override
    public int update(Long id, Tenant admin) {
        admin.setId(id);
        //密码已经加密处理，需要单独修改
        admin.setPassword(null);
        return tenantMapper.updateById(admin);
    }

    @Override
    public int delete(Long id) {
        return tenantMapper.deleteById(id);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        QueryWrapper<TenantAdminRoleRelation> wrapper = new QueryWrapper<>();
        //先删除原来的关系
        wrapper.lambda().eq(TenantAdminRoleRelation::getTenantId, adminId);
        adminRoleRelationMapper.delete(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<TenantAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                TenantAdminRoleRelation roleRelation = new TenantAdminRoleRelation();
                roleRelation.setTenantId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }

            adminRoleRelationMapper.insertList(list);
        }
        return count;
    }

    @Override
    public List<TenantRole> getRoleList(Long adminId) {
        return adminRoleRelationMapper.getRoleList(adminId);
    }

    @Override
    public int updatePermission(List<Long> permissionIds) {

        //删除原所有权限关系
        QueryWrapper<TenantAdminPermissionRelation> wrapper = new QueryWrapper<>();
        adminPermissionRelationMapper.delete(wrapper);
        //获取用户所有角色权限
        List<TenantPermission> permissionList = adminRoleRelationMapper.getRolePermissionList(getCurrentTenantId());
        List<Long> rolePermissionList = permissionList.stream().map(TenantPermission::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<TenantAdminPermissionRelation> relationList = new ArrayList<>();
            //筛选出+权限
            List<Long> addPermissionIdList = permissionIds.stream().filter(permissionId -> !rolePermissionList.contains(permissionId)).collect(Collectors.toList());
            //筛选出-权限
            List<Long> subPermissionIdList = rolePermissionList.stream().filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
            //插入+-权限关系
//            relationList.addAll(convert(getCurrentTenantId(),1,addPermissionIdList));
//            relationList.addAll(convert(getCurrentTenantId(),-1,subPermissionIdList));
            return adminPermissionRelationMapper.insertList(relationList);
        }
        return 0;
    }

    /**
     * 将+-权限关系转化为对象
     */
    private List<TenantAdminPermissionRelation> convert(Long adminId,Integer type,List<Long> permissionIdList) {
        List<TenantAdminPermissionRelation> relationList = permissionIdList.stream().map(permissionId -> {
            TenantAdminPermissionRelation relation = new TenantAdminPermissionRelation();
            relation.setTenantId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }

    @Override
    public List<TenantPermission> getPermissionList(Long adminId) {
        return adminRoleRelationMapper.getPermissionList(adminId);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            throw new ServiceException(DefaultResultType.PARAM_INVALID);
        }
        QueryWrapper<Tenant> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Tenant::getUsername, param.getUsername());
        Tenant umsAdmin = tenantMapper.selectOne(wrapper);
        if(!BCryptUtils.matches(param.getOldPassword(),umsAdmin.getPassword())){
            throw new ServiceException(DefaultResultType.USERNAME_PASSWORD_INCORRECT);
        }
        umsAdmin.setPassword(BCryptUtils.encode(param.getNewPassword()));
        return tenantMapper.updateById(umsAdmin);
    }

//    @Override
//    public Tenant getAdminInfo() {
//        TenantAuthenticationToken token = (TenantAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        Long id = token.getTenantId();
//        Tenant tenant = getById(id);
//        Collection<GrantedAuthority> authorities = token.getAuthorities();
//        List<String> permission = new LinkedList<>();
//        if (CollectionUtil.isNotEmpty(authorities)) {
//            for (GrantedAuthority authority : authorities) {
//                permission.add(authority.getAuthority());
//            }
//        }
//        tenant.setId(null).setPassword("");
//        tenant.setPermission(permission);
//        return tenant;
//    }
//
//    @Override
    public Long getCurrentTenantId() {
//        TenantAuthenticationToken token = (TenantAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        return token.getTenantId();
        return 1L;
    }

}
