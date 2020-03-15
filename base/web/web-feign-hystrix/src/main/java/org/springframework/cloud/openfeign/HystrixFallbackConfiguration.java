package org.springframework.cloud.openfeign;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈少平
 * @date 2019-12-06 21:15
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class HystrixFallbackConfiguration {

    @Configuration
    @ConditionalOnClass(name = "feign.hystrix.HystrixFeign")
    protected static class HystrixFeignTargeterConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public Targeter feignTargeter() {
            return new HystrixFallbackTargeter();
        }
    }
}
