package com.csp.github.rabc.controller;


import com.csp.github.rabc.entity.AdminUser;
import com.csp.github.rabc.service.IAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class AdminUserController {

    @Resource
    IAdminUserService userService;

    @ApiOperation(value = "获取用户信息，包括权限，角色")
    @GetMapping("/{id}")
    public AdminUser userFullInfo(@PathVariable Long id) {
        AdminUser info = userService.getUserInfoWithRolesAndPermissions(id);
        return info;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping
    public AdminUser register(@RequestBody AdminUser user) {
        return userService.register(user);
    }

    @ApiOperation(value = "错误是")
    @GetMapping("/tt")
    public String tt() {
        return "i am adminUser";
    }

}
