package com.csp.github.tenant.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csp.github.base.common.annotation.Create;
import com.csp.github.base.common.entity.PageParam;
import com.csp.github.tenant.dto.TenantPermissionNode;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.service.ITenantPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户权限管理
 */
@Validated
@RestController
@Api(tags = "TenantPermissionController", description = "后台用户权限管理")
public class TenantPermissionController {

    @Resource
    private ITenantPermissionService permissionService;

    @ApiOperation("添加权限")
    @PostMapping(value = "/tenant-admin/permission/create")
    public TenantPermission create(@RequestBody @Validated(value = {Create.class, Default.class}) TenantPermission permission) {
        permissionService.save(permission);
        return permission;
    }

    @ApiOperation("修改权限")
    @PostMapping(value = "/tenant-admin/permission/update")
    public boolean update(@RequestBody TenantPermission permission) {
        return permissionService.updateById(permission);
    }

    @ApiOperation("根据id批量删除权限")
    @DeleteMapping(value = "/tenant-admin/permission/delete")
    public boolean delete(@RequestParam("ids") List<Long> ids) {
        return permissionService.removeByIds(ids);
    }

    @ApiOperation("以层级结构返回所有权限")
    @GetMapping(value = "/tenant-admin/permission/treeList")
    @ResponseBody
    public List<TenantPermissionNode> treeList() {
        return permissionService.treeList();
    }

    @ApiOperation("获取所有权限列表")
    @GetMapping(value = "/tenant-admin/permission/list")
    public IPage<TenantPermission> list(PageParam pageParam) {
        return permissionService.page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
    }
}
