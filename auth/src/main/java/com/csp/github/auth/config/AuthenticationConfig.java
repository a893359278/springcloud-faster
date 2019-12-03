package com.csp.github.auth.config;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author 陈少平
 * @date 2019-11-22 19:19
 */
@Configuration
@EnableAuthorizationServer // 配置授权服务器
public class AuthenticationConfig extends AuthorizationServerConfigurerAdapter {

    // 1、配置哪些客户端是允许被授权的。
    // 配置哪些客户端是允许被授权的。
    // 到时候具体的是从数据库里面配置的
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Resource
    DataSource dataSource;

    // 2、配置如何发放令牌，如何管理令牌
    // 配置使用 jwt 令牌服务
    /**
     * jwt 对称加密密钥
     */
    @Value("${spring.security.oauth2.jwt.signingKey:csp123}")
    private String signingKey;
    @Resource(name = "accessTokenConverter")
    JwtAccessTokenConverter accessTokenConverter;
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter);
    }
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }
    // 配置令牌服务
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());

        // 为了能够使用 jwt 令牌服务，这里需要增强令牌管理服务
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
        tokenServices.setTokenEnhancer(enhancerChain);

        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(10));
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
        return tokenServices;
    }


    // 3、令牌访问端点配置
    // 配置授权类型： authenticationManager，userDetailService，
    // authorizationCodeServices，implicitGrantService，tokenGranter
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private AuthorizationCodeServices authorizationCodeServices;

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager) // 密码模式
                .authorizationCodeServices(authorizationCodeServices) // 授权码模式
                .userDetailsService(userDetailsService)
                .approvalStore(approvalStore())
                .tokenStore(tokenStore())
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);
    }
    // 配置令牌端点的安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("isAuthenticated()")    // 公钥公开
                .checkTokenAccess("permitAll()")    // 检测token公开
                .allowFormAuthenticationForClients();    // 表单认证
    }

}
