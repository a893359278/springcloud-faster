package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import javafx.geometry.Pos;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author 陈少平
 * @date 2019-11-16 19:07
 */
public class PostMappingHandlerAdapter extends AbstractMappingHandlerAdapter<PostMapping>{

    @Override
    public boolean doAdapter(Annotation annotation) {
        return annotation instanceof PostMapping;
    }

    @Override
    public String method() {
        return "POST";
    }

    @Override
    public String[] path() {
        return r.value();
    }
}
