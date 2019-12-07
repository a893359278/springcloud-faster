package com.csp.github.base.web.config;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.DefaultHystrixFallbackFactory;
import org.springframework.cloud.openfeign.HystrixFallbackConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 陈少平
 * @date 2019-12-06 22:41
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import({HystrixFallbackConfiguration.class})
public class DefaultFeignClientsConfiguration implements WebMvcConfigurer {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public DefaultHystrixFallbackFactory defaultHystrixFallbackFactory() {
        DefaultHystrixFallbackFactory defaultHystrixFallbackFactory = new DefaultHystrixFallbackFactory();
        defaultHystrixFallbackFactory.setFallbackHandlerFactory(new DefaultHystrixFallbackHandlerFactory());
        return defaultHystrixFallbackFactory;
    }
}
