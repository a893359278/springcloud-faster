package com.csp.github.base.adminclient.autoconfig;

import javax.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties;
import org.springframework.boot.actuate.health.ShowDetails;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈少平
 * @date 2020-01-24 09:45
 */
@Configuration
public class SpringBootAdminClientAutoConfig implements InitializingBean {

    @Resource
    WebEndpointProperties webEndpointProperties;

    @Resource
    HealthEndpointProperties healthEndpointProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        webEndpointProperties.getExposure().getInclude().add("*");
        healthEndpointProperties.setShowDetails(ShowDetails.ALWAYS);
    }
}
