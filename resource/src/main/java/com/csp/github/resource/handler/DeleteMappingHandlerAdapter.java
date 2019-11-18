package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陈少平
 * @date 2019-11-16 19:07
 */
public class DeleteMappingHandlerAdapter implements MappingHandlerAdapter{

    private DeleteMapping r;

    @Override
    public boolean adapter(Annotation annotation) {
        if (commonAdapter(annotation) && annotation instanceof DeleteMapping) {
            r = (DeleteMapping) annotation;
            return true;
        }
        return false;
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
