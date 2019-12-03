package com.csp.github.log.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈少平
 * @date 2019-11-30 10:48
 */
@Configuration
@ConditionalOnProperty(value = "log.api.invoke.enable", matchIfMissing = true)
public class ServletRequestConfig {

}
