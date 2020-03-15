package org.springframework.cloud.openfeign;

import feign.Feign;
import feign.Target;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;

/**
 * @author 陈少平
 * @date 2019-12-06 21:19
 */
public class HystrixFallbackTargeter implements Targeter {
    @Override
    public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
            Target.HardCodedTarget<T> target) {
        if (!(feign instanceof HystrixFeign.Builder)) {
            return feign.target(target);
        }
        HystrixFeign.Builder builder = (HystrixFeign.Builder) feign;
        SetterFactory setterFactory = getOptional(factory.getName(), context,
                SetterFactory.class);
        if (setterFactory != null) {
            builder.setterFactory(setterFactory);
        }
        Class<?> fallback = factory.getFallback();
        if (fallback != void.class) {
            return targetWithFallback(factory.getName(), context, target, builder, fallback);
        }
        Class<?> fallbackFactory = factory.getFallbackFactory();
        if (fallbackFactory != void.class) {
            return targetWithFallbackFactory(factory.getName(), context, target, builder, fallbackFactory);
        }

        return feign.target(target);
    }

    protected <T> T targetWithFallbackFactory(String feignClientName, FeignContext context,
            Target.HardCodedTarget<T> target,
            HystrixFeign.Builder builder,
            Class<?> fallbackFactoryClass) {
        FallbackFactory<? extends T> fallbackFactory = (FallbackFactory<? extends T>)
                getFromContext("fallbackFactory", feignClientName, context, fallbackFactoryClass, FallbackFactory.class);
        if(fallbackFactory instanceof DefaultHystrixFallbackFactory) {
            DefaultHystrixFallbackFactory hystrixFallbackFactory = (DefaultHystrixFallbackFactory) fallbackFactory;
            hystrixFallbackFactory.setFeignClientClass(target.type());
        }
        return builder.target(target, fallbackFactory);
    }


    protected <T> T targetWithFallback(String feignClientName, FeignContext context,
            Target.HardCodedTarget<T> target,
            HystrixFeign.Builder builder, Class<?> fallback) {
        T fallbackInstance = getFromContext("fallback", feignClientName, context, fallback, target.type());
        return builder.target(target, fallbackInstance);
    }

    protected <T> T getFromContext(String fallbackMechanism, String feignClientName, FeignContext context,
            Class<?> beanType, Class<T> targetType) {
        Object fallbackInstance = context.getInstance(feignClientName, beanType);
        if (fallbackInstance == null) {
            throw new IllegalStateException(String.format(
                    "No " + fallbackMechanism + " instance of type %s found for feign client %s",
                    beanType, feignClientName));
        }

        if (!targetType.isAssignableFrom(beanType)) {
            throw new IllegalStateException(
                    String.format(
                            "Incompatible " + fallbackMechanism + " instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                            beanType, targetType, feignClientName));
        }
        return (T) fallbackInstance;
    }

    protected <T> T getOptional(String feignClientName, FeignContext context,
            Class<T> beanType) {
        return context.getInstance(feignClientName, beanType);
    }
}
