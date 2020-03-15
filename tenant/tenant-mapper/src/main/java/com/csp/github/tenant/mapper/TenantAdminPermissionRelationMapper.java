package com.csp.github.tenant.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csp.github.tenant.entity.TenantAdminPermissionRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) Mapper 接口
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
public interface TenantAdminPermissionRelationMapper extends BaseMapper<TenantAdminPermissionRelation> {

    @SqlParser(filter = true)
    int insertList(@Param("list") List<TenantAdminPermissionRelation> list);


}
