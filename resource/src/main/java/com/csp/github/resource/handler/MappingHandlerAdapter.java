package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 请求处理适配器
 * @author 陈少平
 * @date 2019-11-16 19:06
 */
public interface MappingHandlerAdapter {
    boolean adapter(Annotation annotation) ;
    String method();
    String[] path();

    default boolean commonAdapter(Annotation annotation) {
        return Objects.nonNull(annotation);
    }
}
