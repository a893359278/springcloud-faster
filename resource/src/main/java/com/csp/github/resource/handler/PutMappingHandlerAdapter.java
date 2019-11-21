package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author 陈少平
 * @date 2019-11-16 19:07
 */
public class PutMappingHandlerAdapter extends AbstractMappingHandlerAdapter<PutMapping>{

    @Override
    public boolean doAdapter(Annotation annotation) {
        return annotation instanceof PutMapping;
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
