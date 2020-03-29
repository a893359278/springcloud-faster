package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author 陈少平
 * @date 2019-11-20 18:48
 */
public abstract class AbstractMappingHandlerAdapter<A extends Annotation> implements MappingHandlerAdapter {
    protected A r;

    @SuppressWarnings("unchecked")
    @Override
    public boolean adapter(Annotation annotation) {
        if (Objects.nonNull(annotation) && (doAdapter(annotation))) {
            r = (A) annotation;
            return true;
        }
        return false;
    }

    public abstract boolean doAdapter(Annotation annotation);
}
