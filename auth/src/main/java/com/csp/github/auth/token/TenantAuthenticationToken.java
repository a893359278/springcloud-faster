package com.csp.github.auth.token;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 陈少平
 * @date 2019-12-30 21:57
 */
public class TenantAuthenticationToken extends AbstractAuthenticationToken {

    private String username;
    private String password;
    @Getter
    private Long tenantId;

    public TenantAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String username, String password, Long tenantId) {
        super(authorities);
        this.username = username;
        this.password = password;
        this.tenantId = tenantId;
        super.setAuthenticated(true);
    }

    public TenantAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
        setAuthenticated(false);
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
