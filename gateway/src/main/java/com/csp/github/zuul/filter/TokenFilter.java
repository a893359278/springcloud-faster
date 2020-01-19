//package com.csp.github.zuul.filter;
//
//import cn.hutool.core.util.StrUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.csp.github.base.common.entity.DefaultResultType;
//import com.csp.github.base.common.entity.Result;
//import com.csp.github.base.common.exception.ServiceException;
//import com.csp.github.redis.token.TokenStore;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import java.io.PrintWriter;
//import java.util.Objects;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//
///**
// * 在 zuul 转发之前拦截
// * 校验 token 的合法性
// * @author 陈少平
// * @date 2020-01-17 22:56
// */
//@Slf4j
//@Component
//public class TokenFilter extends ZuulFilter {
//
//    public static final String TOKEN_HEADER = "Authorization";
//    public static final String TOKEN_PREFIX = "Bearer ";
//
//    @Lazy
//    @Autowired
//    TokenStore tokenStore;
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return -1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        String token = resolveToken(request);
//        if (StrUtil.isNotEmpty(token)) {
//            Long tenantId = tokenStore.getTenantRefreshToken(token);
//            if (Objects.isNull(tenantId)) {
//                tenantId = tokenStore.getTenantExpireToken(token);
//                if (Objects.isNull(tenantId)) {
//                    throw new ServiceException(DefaultResultType.NEED_LOGIN);
//                } else {
//                    String uuidToken = tokenStore.generatorUUIDToken();
//                    tokenStore.refreshTenantToken(uuidToken, tenantId);
//                    write(token);
//                    return "SUCCESS";
//                }
//            } else {
//                return "SUCCESS";
//            }
//        } else {
//            return "SUCCESS";
//        }
//    }
//
//    private void write(String token) {
//        try {
//            RequestContext ctx = RequestContext.getCurrentContext();
//            HttpServletResponse response = ctx.getResponse();
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.write(JSONObject.toJSONString(Result.ok(token)));
//        } catch (Exception e) {
//            log.error("网关写刷新 token 失败 " + e.getMessage());
//        }
//   }
//
//    private String resolveToken(HttpServletRequest request) {
//        String header = request.getHeader(TOKEN_HEADER);
//        if (StrUtil.isNotEmpty(header)) {
//            String[] tokens = header.split(TOKEN_PREFIX);
//            if (tokens.length != 2) {
//                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
//            }
//            String token = tokens[1];
//            if (StrUtil.isBlank(token) || token.length() != 36) {
//                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
//            }
//            return token;
//        } else {
//            return null;
//        }
//    }
//
//}
