package com.csp.github.auth.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 陈少平
 * @date 2019-12-29 10:38
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String tokenHeader = "Authorization";
    private String secret = "12345678900987654321qazwsx";
    private int refresh = 7200;
    private int expiration = 604800;
    private String tokenPrefix = "Bearer ";
}
