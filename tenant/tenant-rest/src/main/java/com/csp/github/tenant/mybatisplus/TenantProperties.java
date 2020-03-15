package com.csp.github.tenant.mybatisplus;

import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 陈少平
 * @date 2019-12-29 09:53
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {

    private String column;

    private Set<String> tableFilters;
}
