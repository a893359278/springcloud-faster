package org.springframework.cloud.openfeign;

import cn.hutool.core.lang.Assert;
import feign.hystrix.FallbackFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Scope;

/**
 * @author 陈少平
 * @date 2019-12-06 21:11
 */
@Scope("prototype")
public class DefaultHystrixFallbackFactory implements FallbackFactory<Object> {
    private Class<?> feignClientClass;

    private HystrixFallbackHandlerFactory fallbackHandlerFactory;

    @Override
    public Object create(Throwable cause) {
        Assert.notNull(feignClientClass, "Property 'feignClientClass' must be required!");
        Assert.notNull(fallbackHandlerFactory, "Property 'fallbackHandlerFactory' must be required!");
        return doCreate(cause);
    }

    protected Object doCreate(Throwable cause) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(Thread.currentThread().getContextClassLoader());
        enhancer.setSuperclass(getFeignClientClass());
        enhancer.setCallback(getFallbackHandlerFactory().createHandler(getFeignClientClass(), cause));
        return enhancer.create();
    }

    public HystrixFallbackHandlerFactory getFallbackHandlerFactory() {
        return fallbackHandlerFactory;
    }

    public void setFallbackHandlerFactory(HystrixFallbackHandlerFactory fallbackHandlerFactory) {
        this.fallbackHandlerFactory = fallbackHandlerFactory;
    }

    public Class<?> getFeignClientClass() {
        return feignClientClass;
    }

    public void setFeignClientClass(Class<?> feignClientClass) {
        this.feignClientClass = feignClientClass;
    }
}
