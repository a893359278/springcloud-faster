package com.csp.github.zuul.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.base.web.utils.RequestUtils;
import com.csp.github.redis.token.TokenStore;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * 在 zuul 转发之前拦截
 * 校验 token 的合法性, 校验 token 的 权限，
 * @author 陈少平
 * @date 2020-01-17 22:56
 */
@Slf4j
@Component
public class TokenFilter extends ZuulFilter {

    public static final Set<String> loginUrl = new HashSet<>();

    public static final Set<String> notNeedLoginUrl = new HashSet<>();

    static {
        initLoginUrl();
        initNotNeedLoginUrl();
    }

    private static void initLoginUrl() {
        loginUrl.add("/tenant/tenant/login");
    }

    private static void initNotNeedLoginUrl() {
        // todo 仅为测试路由，无实际意义
        notNeedLoginUrl.add("/tenant/tenant/username/**");
        notNeedLoginUrl.add("/tenant/tenant/test/*");
        notNeedLoginUrl.add("/tenant/*.html");
        notNeedLoginUrl.add("/**/swagger-ui.html");
        notNeedLoginUrl.add("/**/swagger-resources/**");
        notNeedLoginUrl.add("/**/api-docs");

    }

    @Lazy
    @Autowired
    TokenStore tokenStore;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (!isRequestLogin(request)) {
            if (needLogin(request) && isTenantToken(request)) {
                Long tenantId = checkToken(ctx, request);
                checkPermission(tenantId, request.getRequestURI());
                ctx.addZuulRequestHeader(RequestUtils.TENANT_DELIVER_ID, tenantId.toString());
            }
        }
        return null;
    }

    private Long checkToken(RequestContext ctx, HttpServletRequest request) {
        Long tenantId;
        String token = resolveToken(request);
        if (StrUtil.isNotEmpty(token)) {
            tenantId = tokenStore.getTenantRefreshToken(token);
            if (Objects.isNull(tenantId)) {
                tenantId = tokenStore.getTenantExpireToken(token);
                if (Objects.isNull(tenantId)) {
                    throw new ServiceException(DefaultResultType.NEED_LOGIN);
                } else {
                    String uuidToken = tokenStore.generatorUUIDToken();
                    tokenStore.refreshTenantToken(token, uuidToken, tenantId);
                    needFreshToken(ctx.getResponse(), uuidToken);
                }
            }
        } else {
            throw new ServiceException(DefaultResultType.NEED_LOGIN);
        }
        return tenantId;
    }

    private boolean needLogin(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !urlMatch(uri, notNeedLoginUrl);
    }

    private boolean urlMatch(String uri, Set<String> urls) {
        if (CollectionUtil.isNotEmpty(urls)) {
            return  urls.stream().anyMatch(item -> antPathMatcher.match(item, uri));
        } else {
            return false;
        }
    }

    private String trimEndString(String source, String target) {
        if (StrUtil.isNotBlank(source) && source.endsWith(target)) {
            int index = source.lastIndexOf(target);
            return source.substring(0, index);
        }
        return source;
    }


    private boolean isTenantToken(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/tenant");
    }

    private void checkPermission(Long tenantId, String requestURI) {
        List<String> permission = tokenStore.getTenantPermission(tenantId);
        if (CollectionUtil.isNotEmpty(permission)) {
            boolean match = permission.stream().anyMatch(item -> antPathMatcher.match(item, requestURI));
            if (!match) {
                throw new ServiceException(DefaultResultType.ACCESS_DENIED);
            }
        } else {
            throw new ServiceException(DefaultResultType.ACCESS_DENIED);
        }
    }

    private boolean isRequestLogin(HttpServletRequest request) {
        return urlMatch(request.getRequestURI(), loginUrl);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(RequestUtils.TOKEN_HEADER);
        if (StrUtil.isNotEmpty(header)) {
            String[] tokens = header.split(RequestUtils.TOKEN_PREFIX);
            if (tokens.length != 2) {
                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
            }
            String token = tokens[1];
            if (StrUtil.isBlank(token) || token.length() != 32) {
                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
            }
            return token;
        } else {
            return null;
        }
    }

    private void needFreshToken(HttpServletResponse response, String token) {
        throw new ServiceException(DefaultResultType.TOKEN_FRESH.getCode(), token);
    }

}
