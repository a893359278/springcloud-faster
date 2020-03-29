package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 陈少平
 * @date 2019-11-16 21:57
 */
public class RequestMappingHandlerAdapter extends AbstractMappingHandlerAdapter<RequestMapping> {

    @Override
    public boolean doAdapter(Annotation annotation) {
        return annotation instanceof RequestMapping;
    }

    @Override
    public String method() {
        RequestMethod[] m = r.method();
        String tmp = Stream.of(m).map(Enum::name).collect(Collectors.joining());
        if (tmp == null || tmp.equals("")) {
            tmp = "GET";
        }
        return tmp;
    }

    @Override
    public String[] path() {
        return r.value();
    }
}
