package com.csp.github.rabc.controller;


import com.csp.github.base.common.annotation.Create;
import com.csp.github.rabc.entity.AdminUser;
import com.csp.github.rabc.service.IAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
@Api(value = "用户控制器")
@RestController
@RequestMapping("/adminUser")
@Validated
public class AdminUserController {

    @Resource
    IAdminUserService userService;

    @ApiOperation(value = "获取用户信息，包括权限，角色")
    @GetMapping("/{id}")
    public AdminUser userFullInfo(@PathVariable Long id) {
        return userService.getUserInfoWithRolesAndPermissions(id);
    }

    @ApiOperation(value = "通过用户名获取用户")
    @GetMapping
    public AdminUser getUser(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @ApiOperation(value = "获取用户信息，包括权限，角色")
    @GetMapping("/info")
    public AdminUser getUserFullInfoByUsername(@RequestParam String username) {
        return userService.getUserInfoWithRolesAndPermissions(username);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping
    public AdminUser register(@RequestBody @Validated({Create.class, Default.class}) AdminUser user) {
        return userService.register(user);
    }

}
