package com.csp.github.auth.provider;

import com.csp.github.auth.token.TenantAuthenticationToken;
import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.tenant.client.TenantClient;
import com.csp.github.tenant.entity.Tenant;
import com.csp.github.tenant.entity.TenantPermission;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 陈少平
 * @date 2019-12-30 21:58
 */
public class TenantDaoAuthenticationProvider implements AuthenticationProvider {

    @Resource
    TenantClient tenantClient;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TenantAuthenticationToken token = (TenantAuthenticationToken) authentication;
        Tenant unit = tenantClient.getTenantByUsername(token.getPrincipal());
        if (Objects.isNull(unit)) {
            throw new ServiceException(DefaultResultType.NOT_EXIST);
        }
        // 匹配密码
        if (!passwordEncoder.matches(token.getCredentials(), unit.getPassword())) {
            throw new ServiceException(DefaultResultType.USERNAME_PASSWORD_INCORRECT);
        }
        // 查询权限
        List<TenantPermission> list = tenantClient.getPermissionList(unit.getId());
        LinkedList<GrantedAuthority> permission = new LinkedList<>();
        for (TenantPermission p : list) {
            permission.add(new SimpleGrantedAuthority(p.getUri()));
        }
        // 返回 token
        return new TenantAuthenticationToken(permission, unit.getUsername(), unit.getPassword(), unit.getId());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TenantAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
