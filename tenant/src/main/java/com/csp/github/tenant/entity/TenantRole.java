package com.csp.github.tenant.entity;

import com.csp.github.base.common.annotation.Create;
import com.csp.github.base.web.entity.BaseEntity;
import com.csp.github.tenant.enums.RoleStatuEnum;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户角色表
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TenantRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Null
    private Long tenantId;

    /**
     * 名称
     */
    @NotNull(groups = {Create.class})
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 后台用户数量
     */
    private Integer adminCount;

    @NotNull(groups = {Create.class})
    private RoleStatuEnum status;

    @NotNull(groups = {Create.class})
    private Integer sort;


}
