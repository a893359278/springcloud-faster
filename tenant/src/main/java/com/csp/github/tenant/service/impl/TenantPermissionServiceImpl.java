package com.csp.github.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csp.github.tenant.dto.TenantPermissionNode;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.mapper.TenantPermissionMapper;
import com.csp.github.tenant.service.ITenantPermissionService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Service
public class TenantPermissionServiceImpl extends ServiceImpl<TenantPermissionMapper, TenantPermission> implements ITenantPermissionService {

    @Resource
    TenantPermissionMapper permissionMapper;

    @Override
    public List<TenantPermissionNode> treeList() {
        List<TenantPermission> permissionList = permissionMapper.selectList(null);
        List<TenantPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private TenantPermissionNode covert(TenantPermission permission,List<TenantPermission> permissionList){
        TenantPermissionNode node = new TenantPermissionNode();
        BeanUtils.copyProperties(permission,node);
        List<TenantPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission,permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
    
}
