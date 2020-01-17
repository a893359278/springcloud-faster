//package com.csp.github.auth.filter;
//
//import cn.hutool.core.util.StrUtil;
//import com.csp.github.auth.exception.TokenNeedFreshException;
//import com.csp.github.auth.token.TenantAuthenticationToken;
//import com.csp.github.auth.utils.JwtUtils;
//import com.csp.github.auth.utils.JwtUtils.TokenType;
//import com.csp.github.base.common.entity.DefaultResultType;
//import com.csp.github.base.common.exception.ServiceException;
//import io.jsonwebtoken.Claims;
//import java.io.IOException;
//import java.util.List;
//import java.util.Objects;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * @author 陈少平
// * @date 2019-12-27 18:58
// */
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtUtils jwtUtils;
//
//    public JwtTokenFilter(JwtUtils jwtUtils) {
//        this.jwtUtils = jwtUtils;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // option 请求直接放行
//        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // 解析 token
//        String token = resolveToken(request);
//        if (StrUtil.isNotEmpty(token)) {
//            // 存在,校验 token
//            Claims claims = parseToken(token);
//            if (jwtUtils.isNeedFresh(claims)) {
//                // token 刷新
//                throw new TokenNeedFreshException(DefaultResultType.TOKEN_FRESH.getCode(), jwtUtils.getJwtProperties().getTokenPrefix() + jwtUtils.refreshToken(claims));
//            }
//            if (jwtUtils.checkExpiration(claims)) {
//                // token 失效
//                throw new ServiceException(DefaultResultType.NEED_LOGIN);
//            }
//            // 判断用户当前是在请求哪部分接口
//            TokenType type = jwtUtils.getTokenType(claims);
//            if (type == TokenType.API) {
////                MemberAuthenticationToken jwtToken = doApiResolve(claims);
////                SecurityContextHolder.getContext().setAuthentication(jwtToken);
//            } else if (type == TokenType.TENANT) {
//                TenantAuthenticationToken jwtToken = doTenantResolve(claims);
//                SecurityContextHolder.getContext().setAuthentication(jwtToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private TenantAuthenticationToken doTenantResolve(Claims claims) {
//        String username = claims.getSubject();
//        Long tenantId = jwtUtils.getTenantId(claims);
//        List<GrantedAuthority> permissions = jwtUtils.getPermissions(claims);
//        if (Objects.isNull(tenantId) || StrUtil.isBlank(username)) {
//            throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
//        }
//        return new TenantAuthenticationToken(permissions, username, null, tenantId);
//    }
//
////    private MemberAuthenticationToken doApiResolve(Claims claims) {
////        String username = claims.getSubject();
////        Long tenantId = jwtUtils.getTenantId(claims);
////        Long id = jwtUtils.getApiId(claims);
////        if (Objects.isNull(tenantId) || StrUtil.isEmpty(username) || Objects.isNull(id)) {
////            throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
////        }
////        return new MemberAuthenticationToken(Collections.emptyList(), username, "", tenantId, id);
////    }
//
//
//    private Claims parseToken(String token) {
//        Claims claims = jwtUtils.getClaimsFromToken(token);
//        if (Objects.isNull(claims)) {
//            throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
//        }
//        return claims;
//    }
//
//    private String resolveToken(HttpServletRequest request) {
//        String header = request.getHeader(jwtUtils.getJwtProperties().getTokenHeader());
//        if (StrUtil.isNotEmpty(header)) {
//            String[] tokens = header.split(jwtUtils.getJwtProperties().getTokenPrefix());
//            if (tokens.length != 2) {
//                throw new ServiceException(DefaultResultType.TOKEN_ILLEGAL);
//            }
//            return tokens[1];
//        } else {
//            return null;
//        }
//    }
//
//}
