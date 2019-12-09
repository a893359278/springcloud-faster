package com.csp.github.base.web.hystrix;

import com.csp.github.base.common.entity.Result;
import java.lang.reflect.Method;
import org.springframework.cloud.openfeign.HystrixFallbackHandler;

/**
 * @author 陈少平
 * @date 2019-12-06 22:42
 */
public class DefaultHystrixFallbackHandler extends HystrixFallbackHandler {

    DefaultHystrixFallbackHandler(Class<?> feignClientClass, Throwable cause) {
        super(feignClientClass, cause);
    }


    @Override
    protected Object handle(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        return Result.fail();
    }
}
