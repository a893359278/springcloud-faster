package com.csp.github.log.listener;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author 陈少平
 * @date 2019-11-29 19:43
 */
@Component
public class ApiInvokedHandledListener implements ApplicationListener<ServletRequestHandledEvent>, MethodInterceptor,
        ApplicationEventPublisherAware {


    @Nullable
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        log.info(event.getMethod());
        log.info(event.getRequestUrl());
        log.info(event.getDescription());
        log.info(event.getShortDescription());
        System.out.println(event);
        DispatcherServlet servlet = (DispatcherServlet) event.getSource();
        long processingTimeMillis = event.getProcessingTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Object source = event.getSource();
        System.out.println(processingTimeMillis);
    }

    private static final Logger log = LoggerFactory.getLogger(ApiInvokedHandledListener.class);

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        applicationEventPublisher.publishEvent(retVal);
        return retVal;
    }
}
