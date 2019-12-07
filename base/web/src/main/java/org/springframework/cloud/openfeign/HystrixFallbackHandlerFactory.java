package org.springframework.cloud.openfeign;

/**
 * @author 陈少平
 * @date 2019-12-06 21:18
 */
public interface HystrixFallbackHandlerFactory {
    public HystrixFallbackHandler createHandler(Class<?> feignClientClass, Throwable cause);
}
