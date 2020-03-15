package com.csp.github.tenant.entity;

import com.csp.github.base.common.annotation.Create;
import com.csp.github.base.web.entity.BaseEntity;
import com.csp.github.tenant.enums.PermissionTypeEnum;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户权限表
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TenantPermission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Null
    private Long tenantId;

    /**
     * 父级权限id
     */
    @NotNull(groups = {Create.class})
    private Long pid;

    /**
     * 名称
     */
    @NotBlank(groups = {Create.class})
    private String name;

    /**
     * 权限值
     */
    @NotBlank(groups = {Create.class})
    private String value;

    /**
     * 图标
     */
    @NotBlank(groups = {Create.class})
    private String icon;

    /**
     * 权限类型：1->目录；2->菜单；3->按钮（接口绑定权限）
     */
    @NotNull(groups = {Create.class})
    private PermissionTypeEnum type;

    /**
     * 前端资源路径
     */
    private String uri;

    /**
     * 启用状态；0->禁用；1->启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;


}
