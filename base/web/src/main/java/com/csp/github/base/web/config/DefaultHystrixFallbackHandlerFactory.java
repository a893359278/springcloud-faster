package com.csp.github.base.web.config;

import org.springframework.cloud.openfeign.HystrixFallbackHandler;
import org.springframework.cloud.openfeign.HystrixFallbackHandlerFactory;

/**
 * @author 陈少平
 * @date 2019-12-06 22:43
 */
public class DefaultHystrixFallbackHandlerFactory implements HystrixFallbackHandlerFactory {

    @Override
    public HystrixFallbackHandler createHandler(Class<?> feignClientClass, Throwable cause) {
        return new DefaultHystrixFallbackHandler(feignClientClass, cause);
    }
}
