package com.csp.github.resource.handler;

import java.lang.annotation.Annotation;

/**
 * 请求处理适配器
 * @author 陈少平
 * @date 2019-11-16 19:06
 */
public interface MappingHandlerAdapter {

    /**
     * Adapting GetMapping、DeleteMapping、PutMapping、PostMapping、RequestMapping
     * @param annotation
     * @return
     */
    boolean adapter(Annotation annotation) ;

    /**
     * @return head (GET 、 PUT 、 POST 、 DELETE) of current request
     */
    String method();

    /**
     * @return  url of current request
     */
    String[] path();
}
