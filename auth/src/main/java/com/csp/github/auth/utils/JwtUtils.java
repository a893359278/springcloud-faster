//package com.csp.github.auth.utils;
//
//import cn.hutool.core.date.DateField;
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.StrUtil;
//import com.alibaba.fastjson.JSON;
//import com.csp.github.auth.config.JwtProperties;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
///**
// * @author 陈少平
// * @date 2019-12-27 19:26
// */
//@Slf4j
//@EnableConfigurationProperties(JwtProperties.class)
//@Configuration
//public class JwtUtils {
//
//    private static final String CLAIM_KEY_TENANT = "tenant";
//    private static final String CLAIM_KEY_ID = "id";
//    private static final String CLAIM_PERMISSION = "permission";
//
//    private static final String CLAIM_TOKEN_TYPE = "token_type";
//
//    @Resource
//    @Getter
//    @Setter
//    private JwtProperties jwtProperties;
//
//    /**
//     * 从token中获取JWT中的负载
//     */
//    public Claims getClaimsFromToken(String token) {
//        Claims claims = null;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(jwtProperties.getSecret())
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            log.info("JWT格式验证失败:{}", token);
//        }
//        return claims;
//    }
//
//    public Long getApiId(Claims claims) {
//        return claims.get(CLAIM_KEY_ID, Long.class);
//    }
//
//    public Long getTenantId(Claims claims) {
//        return claims.get(CLAIM_KEY_TENANT, Long.class);
//    }
//
//    public List<GrantedAuthority> getPermissions(Claims claims) {
//        List<GrantedAuthority> list = new ArrayList<>();
//        String s = claims.get(CLAIM_PERMISSION, String.class);
//        if (StrUtil.isNotBlank(s)) {
//            List<String> permission = JSON.parseArray(s, String.class);
//            for (String p : permission) {
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(p);
//                list.add(authority);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 判断是否过期
//     * @param claims
//     * @return true: 过期， false 未过期
//     */
//    public boolean checkExpiration(Claims claims) {
//        return claims.getExpiration().before(new Date());
//    }
//
//    /**
//     * 获取 token 类型
//     * @param claims
//     * @return
//     */
//    public TokenType getTokenType(Claims claims) {
//        String type = claims.get(CLAIM_TOKEN_TYPE).toString();
//        if (type.equals(TokenType.API.name())) {
//            return TokenType.API;
//        } else {
//            return TokenType.TENANT;
//        }
//    }
//
//    /**
//     * 创建 会员 token
//     * @return
//     */
//    public String createApiToken(String username, Long unitId, Long id) {
//        Map<String, Object> map = new HashMap<>();
//        map.put(CLAIM_TOKEN_TYPE, TokenType.API);
//        map.put(CLAIM_KEY_TENANT, unitId);
//        map.put(CLAIM_KEY_ID, id);
//        return createToken(username, map);
//    }
//
//    /**
//     * 生成 token
//     * @return
//     */
//    private String createToken(String username, Map<String, Object> claims) {
//        Date validity = DateUtil.offset(new Date(), DateField.SECOND, jwtProperties.getExpiration());
//        return createToken(username, claims, validity);
//    }
//
//    private String createToken(String username, Map<String, Object> clams, Date expiration) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .addClaims(clams)
//                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
//                .setExpiration(expiration)
//                .compact();
//    }
//
//
//    public String createUnitToken(String username, Long unitId, Collection<GrantedAuthority> authorities) {
//        Map<String, Object> map = new HashMap<>();
//        map.put(CLAIM_TOKEN_TYPE, TokenType.TENANT);
//        map.put(CLAIM_KEY_TENANT, unitId);
//        map.put(CLAIM_PERMISSION, JSON.toJSONString(authorities));
//        return createToken(username, map);
//    }
//
//    /**
//     * 判断 token 是否需要刷新
//     * @param claims
//     * @return
//     */
//    public boolean isNeedFresh(Claims claims) {
//        DateTime refreshTime = DateUtil.offset(claims.getIssuedAt(), DateField.SECOND, jwtProperties.getRefresh());
//        return refreshTime.before(new Date());
//    }
//
//    /**
//     * 刷新token
//     * @param claims
//     * @return
//     */
//    public String refreshToken(Claims claims) {
//        DateTime refreshTime = DateUtil.offset(new Date(), DateField.SECOND, jwtProperties.getRefresh());
//        claims.setIssuedAt(refreshTime);
//        return createToken(claims.getSubject(), claims, claims.getExpiration());
//    }
//
//    // token 类型
//    // 定义此类的目的是：为了区分 token 类型
//    public enum TokenType {
//        API,
//        TENANT
//    }
//
//}
