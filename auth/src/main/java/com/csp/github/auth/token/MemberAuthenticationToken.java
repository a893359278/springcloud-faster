package com.csp.github.auth.token;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 陈少平
 * @date 2019-12-26 23:59
 */
public class MemberAuthenticationToken extends AbstractAuthenticationToken {

    private String username;
    private String password;
    private Long tenantId;
    private Long id;

    public MemberAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String username, String password, Long tenantId, Long id) {
        super(authorities);
        this.username = username;
        this.password = password;
        this.tenantId = tenantId;
        this.id = id;
        super.setAuthenticated(true);
    }

    public MemberAuthenticationToken(String principal, String credentials, Long tenantId) {
        super(null);
        this.username = principal;
        this.password = credentials;
        this.tenantId = tenantId;
        setAuthenticated(false);
    }

    public MemberAuthenticationToken(String principal, String credentials, Long tenantId,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = principal;
        this.password = credentials;
        this.tenantId = tenantId;
        super.setAuthenticated(true); // must use super, as we override
    }

    public Long getId() {
        return id;
    }

    public Long getUnitId() {
        return tenantId;
    }

    @Override
    public String getCredentials() {
        return password;
    }

    @Override
    public String getPrincipal() {
        return username;
    }
}
