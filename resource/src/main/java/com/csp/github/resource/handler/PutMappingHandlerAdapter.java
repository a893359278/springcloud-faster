package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author 陈少平
 * @date 2019-11-16 19:07
 */
public class PutMappingHandlerAdapter implements MappingHandlerAdapter{

    private PutMapping r;

    @Override
    public boolean adapter(Annotation annotation) {
        if (commonAdapter(annotation) && annotation instanceof PutMapping) {
            r = (PutMapping) annotation;
            return true;
        }
        return false;
    }

    @Override
    public String method() {
        return "PUT";
    }

    @Override
    public String[] path() {
        return r.value();
    }
}
