package com.csp.github.tenant.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csp.github.tenant.dto.TenantParam;
import com.csp.github.tenant.dto.UpdateAdminPasswordParam;
import com.csp.github.tenant.entity.Tenant;
import com.csp.github.tenant.entity.TenantPermission;
import com.csp.github.tenant.service.ITenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
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
 * 后台用户管理
 * Created by mestacro on 2018/4/26.
 */
@Validated
@Slf4j
@RestController
@Api(tags = "TenantController", description = "单元后台用户管理")
@RequestMapping("/tenant")
public class TenantController {

    @Resource
    private ITenantService unitService;


    @ApiOperation(value = "通过 username 获取租户")
    @GetMapping("/tenant/username")
    public Tenant getTenantByUsername(@NotEmpty String username) {
        return unitService.getAdminByUsername(username);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public Tenant register(@RequestBody TenantParam umsAdminParam) {
        return unitService.register(umsAdminParam);
    }

//    @ApiOperation(value = "用户登录，返回token")
//    @PostMapping(value = "/login")
//    public String login(@RequestBody TenantLoginParam umsAdminLoginParam) {
//        return unitService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
//    }

//    @ApiOperation("退出登录")
//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            log.debug("Invalidating session: " + session.getId());
//            session.invalidate();
//        }
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(null);
//        SecurityContextHolder.clearContext();
//    }

//    @ApiOperation(value = "获取当前登录用户信息")
//    @GetMapping(value = "/info")
//    public Tenant getAdminInfo() {
//        return unitService.getAdminInfo();
//    }


    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    public IPage<Tenant> list(@RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return unitService.list(name, pageSize, pageNum);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/{id}")
    public Tenant getItem(@PathVariable Long id) {
        return unitService.getItem(id);
    }

    @ApiOperation("修改指定用户信息")
    @PutMapping(value = "/update/{id}")
    public int update(@PathVariable Long id, @RequestBody Tenant admin) {
        return unitService.update(id, admin);
    }

    @ApiOperation("修改指定用户密码")
    @PutMapping(value = "/updatePassword")
    public int updatePassword(@RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        return unitService.updatePassword(updatePasswordParam);
    }

    @ApiOperation("删除指定用户信息")
    @DeleteMapping(value = "/delete/{id}")
    public int delete(@PathVariable Long id) {
        return unitService.delete(id);
    }

//    @ApiOperation("给用户分配角色")
//    @PostMapping(value = "/role/update")
//    public int updateRole(@RequestBody List<Long> roleIds) {
//        return unitService.updateRole(unitService.getCurrentTenantId(), roleIds);
//    }

//    @ApiOperation("获取指定用户的角色")
//    @GetMapping(value = "/role")
//    public List<TenantRole> getRoleList() {
//        return unitService.getRoleList(unitService.getCurrentTenantId());
//    }

    @ApiOperation("给用户分配+-权限")
    @PutMapping(value = "/permission/update")
    public int updatePermission(@RequestBody @NotEmpty List<Long> permissionIds) {
        return unitService.updatePermission(permissionIds);
    }
//
//    @ApiOperation("获取用户所有权限（包括+-权限）")
//    @GetMapping(value = "/permission")
//    public List<TenantPermission> getPermissionList() {
//        return unitService.getPermissionList(unitService.getCurrentTenantId());
//    }

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @GetMapping(value = "/permission")
    public List<TenantPermission> getPermissionList(@NotNull Long tenantId) {
        return unitService.getPermissionList(tenantId);
    }
}
