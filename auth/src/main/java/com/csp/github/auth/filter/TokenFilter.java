package com.csp.github.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.redis.token.TokenStore;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 在 zuul 转发之前拦截
 * 校验 token 的合法性
 * @author 陈少平
 * @date 2020-01-17 22:56
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";


    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    TokenStore tokenStore;

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(TOKEN_HEADER);
        if (StrUtil.isNotEmpty(header)) {
            String[] tokens = header.split(TOKEN_PREFIX);
            if (tokens.length != 2) {
                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
            }
            String token = tokens[1];
            if (StrUtil.isBlank(token) || token.length() != 36) {
                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
            }
            return token;
        } else {
            return null;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!shouldFilter(request)) {
            filterChain.doFilter(request, response);
            return ;
        }

        String token = resolveToken(request);
        if (StrUtil.isNotEmpty(token)) {
            Long tenantId = tokenStore.getTenantRefreshToken(token);
            if (Objects.isNull(tenantId)) {
                tenantId = tokenStore.getTenantExpireToken(token);
                if (Objects.isNull(tenantId)) {
                    throw new ServiceException(DefaultResultType.NEED_LOGIN);
                } else {
                    String uuidToken = tokenStore.generatorUUIDToken();
                    tokenStore.refreshTenantToken(uuidToken, tenantId);
                    writeTokenHeader(response, token);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeTokenHeader(HttpServletResponse response, String token) {
        response.setHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
    }

    private boolean shouldFilter(HttpServletRequest request) {
        return !request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name());
    }
}
