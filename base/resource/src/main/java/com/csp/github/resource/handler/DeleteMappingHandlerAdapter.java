package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陈少平
 * @date 2019-11-16 19:07
 */
public class DeleteMappingHandlerAdapter extends AbstractMappingHandlerAdapter<DeleteMapping>{

    @Override
    public boolean doAdapter(Annotation annotation) {
        return annotation instanceof DeleteMapping;
    }

    @Override
    public String method() {
        return "DELETE";
    }

    @Override
    public String[] path() {
        return r.value();
    }
}
