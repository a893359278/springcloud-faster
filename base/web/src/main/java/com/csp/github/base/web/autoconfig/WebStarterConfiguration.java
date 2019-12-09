package com.csp.github.base.web.autoconfig;

import com.csp.github.base.web.advice.RestResultResponseAdvice;
import com.csp.github.base.web.exception.DefaultGlobalExceptionHandlerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动装配
 * @author 陈少平
 * @date 2019-12-09 19:07
 */

@ConditionalOnProperty(value = "rest.enable", matchIfMissing = true, havingValue = "true")
@Import({RestResultResponseAdvice.class, DefaultGlobalExceptionHandlerAdvice.class})
@Configuration
public class WebStarterConfiguration {

}
