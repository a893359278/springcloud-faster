package com.csp.github.auth.token;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 陈少平
 * @date 2019-12-01 14:18
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private String code;

    private String username;

    public SmsAuthenticationToken(String username, String code) {
        super(null);
        this.username = username;
        this.code = code;
        setAuthenticated(false);
    }

    public SmsAuthenticationToken(String username, String code,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.code = code;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

}
