package com.csp.github.tenant;

//import com.csp.github.base.web.annotation.StarterApplication;

import com.csp.github.base.web.annotation.StarterApplication;
import com.csp.github.swagger.autoconfig.EnableSwagger;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2019-11-16 15:02
 */
@RestController
@EnableApolloConfig
@EnableSwagger
@StarterApplication
@MapperScan("com.csp.github.tenant.mapper")
public class TenantApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TenantApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
