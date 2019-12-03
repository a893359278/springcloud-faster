package com.csp.github.auth.filter;

import com.csp.github.auth.config.WebSecurityConfig;
import com.csp.github.auth.token.SmsAuthenticationToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 短信登录拦截器
 * @author 陈少平
 * @date 2019-12-01 13:34
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String USERNAME = "username";
    public static final String CODE = "code";

    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher(WebSecurityConfig.SMS_CODE_PATTERN, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "认证方法不支持" + request.getMethod());
        }

        String username = request.getParameter(USERNAME);
        String code = request.getParameter(CODE);

        if (username == null) {
            username = "";
        }

        if (code == null) {
            code = "";
        }

        username = username.trim();

        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(username, code);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
