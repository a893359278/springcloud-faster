package com.csp.github.log.listener;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author 陈少平
 * @date 2019-11-29 19:43
 */
@Component
public class ApiInvokedHandledListener implements ApplicationListener<ServletRequestHandledEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApiInvokedHandledListener.class);

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        int code = event.getStatusCode();
        log.info(event.getMethod());
        log.info(event.getRequestUrl());
        log.info(event.getDescription());
        log.info(event.getShortDescription());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Object source = event.getSource();
    }

}
