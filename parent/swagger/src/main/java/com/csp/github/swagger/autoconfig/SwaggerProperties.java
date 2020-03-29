package com.csp.github.swagger.autoconfig;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 陈少平
 * @date 2019-12-08 11:48
 */
@Data
@Accessors(chain = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
public class SwaggerProperties {
    public static final String PREFIX = "swagger";
    /**
     * 是否开启swagger
     **/
    private Boolean enabled = true;
    private String host = "localhost:11110";
    private String version = "2.0";
    private String description = "swagger2 api 文档";
    private String license;
    private String licenseUrl;
    private String email = "18250073990@163.com";
    private String creator = "陈少平";
    private String url = "https://segmentfault.com/u/xinwusitiandikuan/about";
    private String authKey = "Authentication";
    private String excludedEnv = "!prod";
}
