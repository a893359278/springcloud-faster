package com.csp.github.bootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author 陈少平
 * @date 2019-11-30 12:14
 */
@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootAdminApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
