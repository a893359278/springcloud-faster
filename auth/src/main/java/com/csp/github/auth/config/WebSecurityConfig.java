package com.csp.github.auth.config;

import com.csp.github.auth.filter.TokenFilter;
import com.csp.github.auth.hanlder.AccessDeniedJsonHandler;
import com.csp.github.auth.hanlder.AuthenticationJsonEntryPoint;
import com.csp.github.redis.token.TokenStore;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 陈少平
 * @date 2019-11-20 23:37
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 注入 处理器
    @Bean(name = "accessDeniedHandler")
    public AccessDeniedJsonHandler accessDeniedJsonHandler() {
        return new AccessDeniedJsonHandler();
    }
    @Bean("authenticationEntryPoint")
    public AuthenticationJsonEntryPoint authenticationJsonEntryPoint() {
        return new AuthenticationJsonEntryPoint();
    }


    // 1、定义安全拦截机制
    // 添加自己的过滤器
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(authenticationJsonEntryPoint())
                .accessDeniedHandler(accessDeniedJsonHandler())

                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()

                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                // 静态资源等等
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.jpeg",
                        "/**/*.gif",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.ico",
                        "/webSocket/**"
                ).permitAll()
                // swagger 文档
                .antMatchers("/**/swagger-ui.html").permitAll()
                .antMatchers("/**/swagger-resources/**").permitAll()
                .antMatchers("/**/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // 文件
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/file/**").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/tenant/login").permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Resource
    TokenStore tokenStore;
    @Bean
    public TokenFilter tokenFilter() {
        TokenFilter filter = new TokenFilter();
        filter.setTokenStore(tokenStore);
        return filter;
    }

//    @Resource
//    JwtUtils jwtUtils;
//    @Bean
//    public JwtTokenFilter jwtTokenFilter() {
//        return new JwtTokenFilter(jwtUtils);
//    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }

    // 3、配置加密策略
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 4、配置认证管理器。认证管理器，管理所有的认证方式
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 5、配置 认证提供者。使用的是 适配者设计模式。
    // 因为配置了 AuthenticationManagerBuilder。因此，不会添加 DaoAuthenticationProvider。这里手动添加
    // 如果有自定义的 认证提供者，应该是往这里面添加
//    @Override
//    public void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.authenticationProvider(tenantDaoAuthenticationProvider());
//                .authenticationProvider(memberDaoSmsAuthenticationProvider());
//    }

    // 配置 spring security 提供的默认 DaoAuthenticationProvider。
    // DaoAuthenticationProvider 默认使用的 DelegatingPasswordEncoder 加密策略 有点恶心。。。
    // 比如使用了 BCryptPasswordEncoder ， 那么在密码比对的时候， 需要在密码的前缀加上 {bcrypt}


//    @Bean
//    public MemberDaoSmsAuthenticationProvider memberDaoSmsAuthenticationProvider() {
//        return new MemberDaoSmsAuthenticationProvider();
//    }

//    @Bean
//    public TenantDaoAuthenticationProvider tenantDaoAuthenticationProvider() {
//        return new TenantDaoAuthenticationProvider();
//    }


    /**
     * 配置跨域
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }
}
