package com.csp.github.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.ServiceException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author 陈少平
 * @date 2020-01-17 22:56
 */
public class TokenFilter extends ZuulFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
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
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = resolveToken(request);
        if (StrUtil.isNotEmpty(token)) {
            return null;
        } else {
            return null;
        }
    }

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

}
