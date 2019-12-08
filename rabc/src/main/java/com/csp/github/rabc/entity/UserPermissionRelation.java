package com.csp.github.rabc.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户和资源关系表
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserPermissionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 资源id
     */
    private Long permissionId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;


}
