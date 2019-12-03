package com.csp.github.auth.config;

import com.csp.github.auth.filter.SmsAuthenticationFilter;
import com.csp.github.auth.filter.VerificationCodeAuthenticationFilter;
import com.csp.github.auth.hanlder.AccessDeniedJsonHandler;
import com.csp.github.auth.hanlder.AuthenticationFailureJsonHandler;
import com.csp.github.auth.hanlder.AuthenticationJsonEntryPoint;
import com.csp.github.auth.hanlder.AuthenticationSuccessJsonHandler;
import com.csp.github.auth.hanlder.LogoutSuccessJsonHandler;
import com.csp.github.auth.provider.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 陈少平
 * @date 2019-11-20 23:37
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String IMAGE_CODE_LOGIN_PATTERN = "/imageCode/login*";
    public static final String SMS_CODE_PATTERN = "/sms/login*";


    // 注入 处理器
    @Bean(name = "accessDeniedHandler")
    public AccessDeniedJsonHandler accessDeniedJsonHandler() {
        return new AccessDeniedJsonHandler();
    }
    @Bean("authenticationEntryPoint")
    public AuthenticationJsonEntryPoint authenticationJsonEntryPoint() {
        return new AuthenticationJsonEntryPoint();
    }
    @Bean("authenticationFailureHandler")
    public AuthenticationFailureJsonHandler authenticationFailureJsonHandler() {
        return new AuthenticationFailureJsonHandler();
    }
    @Bean("authenticationSuccessHandler")
    public AuthenticationSuccessJsonHandler authenticationSuccessJsonHandler() {
        return new AuthenticationSuccessJsonHandler();
    }
    @Bean("logoutSuccessHandler")
    public LogoutSuccessJsonHandler logoutSuccessJsonHandler() {
        return new LogoutSuccessJsonHandler();
    }


    // 1、定义安全拦截机制
    // 添加自己的过滤器
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .authenticationEntryPoint(authenticationJsonEntryPoint())

                .and().authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers(SMS_CODE_PATTERN).permitAll()
                .antMatchers(IMAGE_CODE_LOGIN_PATTERN).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .successHandler(authenticationSuccessJsonHandler())
                .failureHandler(authenticationFailureJsonHandler())

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessJsonHandler())
                .permitAll();

        http.exceptionHandling().accessDeniedHandler(accessDeniedJsonHandler());
    }

    // springboot 默认加载 filter 组件，这里又配置了 bean，因此被加载2次
    // 这里的配置则是为了取消 自动加载的 filter 组件。
    @Bean
    public FilterRegistrationBean registrationBean1(@Qualifier("smsAuthenticationFilter") SmsAuthenticationFilter filter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.setEnabled(false);
        return bean;
    }
    @Bean
    public FilterRegistrationBean registrationBean2(@Qualifier("verificationCodeAuthenticationFilter") VerificationCodeAuthenticationFilter filter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.setEnabled(false);
        return bean;
    }


    // 装配自己的过滤器
    @Bean
    public SmsAuthenticationFilter smsAuthenticationFilter() throws Exception {
        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(authenticationSuccessJsonHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureJsonHandler());
        return filter;
    }
    @Bean
    public VerificationCodeAuthenticationFilter verificationCodeAuthenticationFilter() throws Exception{
        VerificationCodeAuthenticationFilter filter = new VerificationCodeAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(authenticationSuccessJsonHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureJsonHandler());
        return filter;
    }


    // 2、定义用户信息服务（查询用户信息）
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
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
    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(smsAuthenticationProvider());
    }
    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider() {
        return new SmsAuthenticationProvider();
    }

    // 配置 spring security 提供的默认 DaoAuthenticationProvider。
    // DaoAuthenticationProvider 默认使用的 DelegatingPasswordEncoder 加密策略 有点恶心。。。
    // 比如使用了 BCryptPasswordEncoder ， 那么在密码比对的时候， 需要在密码的前缀加上 {bcrypt}
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }
}
