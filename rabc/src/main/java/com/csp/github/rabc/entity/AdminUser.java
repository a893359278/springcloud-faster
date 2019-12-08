package com.csp.github.rabc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.csp.github.base.common.annotation.Create;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @Null(groups = {Create.class})
    private Long id;

    /**
     * 用户名
     */
    @NotNull(groups = {Create.class})
    private String username;

    /**
     * 用户密码密文
     */
    @NotNull(groups = {Create.class})
    private String password;

    /**
     * 用户姓名
     */
    @NotNull(groups = {Create.class})
    private String name;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 用户手机
     */
    @NotNull(groups = {Create.class})
    private String mobile;

    /**
     * 简介
     */
    private String description;

    /**
     * 是否已删除；1：已删除，2：未删除
     */
    @TableLogic
    @Null(groups = {Create.class})
    private Integer deleted;

    /**
     * 是否有效用户
     */
    @Null(groups = {Create.class})
    private Boolean enabled;

    /**
     * 账号是否未过期
     */
    @Null(groups = {Create.class})
    private Boolean accountNonExpired;

    /**
     * 密码是否未过期
     */
    @Null(groups = {Create.class})
    private Boolean credentialsNonExpired;

    /**
     * 是否未锁定
     */
    @Null(groups = {Create.class})
    private Boolean accountNonLocked;

    /**
     * 创建时间
     */
    @Null(groups = {Create.class})
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Null(groups = {Create.class})
    private LocalDateTime updatedTime;

    private List<Role> roles;
    private List<Permission> permissions;

}
