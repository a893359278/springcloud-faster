package com.csp.github.resource.collection;

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认拦截 RestController, Controller 注解的控制器
 * 此收集器，会把 springMVC 默认的 BasicErrorController 收集进来
 * @author 陈少平
 * @date 2019-11-15 23:27
 */
public class RestResourceFilterDefaultStrategy implements ResourceFilterStrategy{

    @Override
    public boolean filter(Object bean, String name) {
        Class<?> cls = bean.getClass();
        return (Objects.nonNull(cls.getAnnotation(RestController.class)) || Objects.nonNull(cls.getAnnotation(Controller.class)));
    }
}
