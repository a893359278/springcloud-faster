package com.csp.github.tenant.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csp.github.base.common.annotation.Create;
import com.csp.github.base.common.annotation.Update;
import com.csp.github.base.web.entity.PageParam;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.entity.TenantRole;
import com.csp.github.tenant.service.ITenantRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户角色管理
 * Created by macro on 2018/9/30.
 */
@Validated
@RestController
@Api(tags = "TenantRoleController", description = "后台用户角色管理")
public class TenantRoleController {

    @Resource
    private ITenantRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/unit-admin/role/create")
    public TenantRole create(@RequestBody @Validated(value = {Create.class, Default.class}) TenantRole role) {
        roleService.save(role);
        return role;
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "/unit-admin/role/update")
    public boolean update(@RequestBody @Validated(value = {Update.class, Default.class}) TenantRole role) {
        return roleService.updateById(role);
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping(value = "/unit-admin/role/delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> ids) {
        return roleService.removeByIds(ids);
    }

    @ApiOperation("获取相应角色权限")
    @GetMapping(value = "/unit-admin/role/permission/{roleId}")
    public List<TenantPermission> getPermissionList(@PathVariable Long roleId) {
        return roleService.getPermissionList(roleId);
    }

    @ApiOperation("修改角色权限")
    @PutMapping(value = "/unit-admin/role/permission/update")
    public int updatePermission(@RequestParam @NotNull Long roleId,
                                 @RequestParam("permissionIds") @NotEmpty List<Long> permissionIds) {
        return roleService.updatePermission(roleId, permissionIds);
    }

    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/unit-admin/role/list")
    public IPage<TenantRole> list(PageParam pageParam) {
        return roleService.page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
    }

}
