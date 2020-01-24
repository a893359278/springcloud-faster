package com.csp.github.zuul.config;

import com.csp.github.swagger.autoconfig.SwaggerProperties;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @author 陈少平
 * @date 2019-12-08 13:47
 */
@Configuration
@Primary
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class SwaggerDocumentConfig implements SwaggerResourcesProvider {
    private final RouteLocator routeLocator;
    @Resource
    SwaggerProperties swaggerProperties;

    public SwaggerDocumentConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        routes.forEach(route -> resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"), swaggerProperties.getVersion())));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
