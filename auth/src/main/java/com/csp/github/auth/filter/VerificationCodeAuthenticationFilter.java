package com.csp.github.auth.filter;

import com.csp.github.auth.config.WebSecurityConfig;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author 陈少平
 * @date 2019-12-01 14:46
 */
public class VerificationCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String IMAGE_CODE = "imageCode";

    public VerificationCodeAuthenticationFilter() {
       setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(WebSecurityConfig.IMAGE_CODE_LOGIN_PATTERN, HttpMethod.POST.name()));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String imageCode = request.getParameter(IMAGE_CODE);
        System.out.println(imageCode);
        if (Objects.isNull(imageCode) || "".equals(imageCode)) {
            throw new AuthenticationServiceException("验证码不能为空！");
        }
        // todo 获取验证码。。。
        return super.attemptAuthentication(request, response);
    }

}
