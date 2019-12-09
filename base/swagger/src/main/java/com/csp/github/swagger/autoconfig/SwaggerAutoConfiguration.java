package com.csp.github.swagger.autoconfig;

import io.swagger.annotations.Api;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 陈少平
 * @date 2019-12-08 11:54
 */
@ConditionalOnClass({EnableSwagger2.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true, havingValue = "true")
@Profile({"swagger.excluded-env:!prod"})
@Configuration
@EnableSwagger2
public class SwaggerAutoConfiguration implements EnvironmentAware {

    @Resource
    private SwaggerProperties swaggerProperties;

    private Environment environment;

    @Autowired(required = false)
    private Set<AlternateTypeRule> alternateTypeRules;

    public SwaggerAutoConfiguration() {
        this.alternateTypeRules = Collections.EMPTY_SET;
    }


    @Bean
    @ConditionalOnMissingBean({Docket.class})
    public Docket docket(ApiInfo apiInfo) {
        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo(apiInfo)
                .alternateTypeRules((AlternateTypeRule[]) this.alternateTypeRules.toArray(new AlternateTypeRule[0]))
                .securityContexts(Collections.singletonList(this.securityContext()))
                .securitySchemes(Collections.singletonList(this.apiKey()))
                .host(StringUtils.isEmpty(this.swaggerProperties.getHost()) ? null : this.swaggerProperties.getHost())
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    @ConditionalOnMissingBean({ApiInfo.class})
    public ApiInfo getApiInfo() {
        String applicationName = environment.getProperty("spring.application.name", "陈少平");
        return (new ApiInfoBuilder())
                .version(this.swaggerProperties.getVersion())
                .title(applicationName)
                .description(this.swaggerProperties.getDescription())
                .license(this.swaggerProperties.getLicense())
                .licenseUrl(this.swaggerProperties.getLicenseUrl())
                .contact(new Contact(this.swaggerProperties.getCreator(), this.swaggerProperties.getUrl(), this.swaggerProperties.getEmail())).build();
    }

    private ApiKey apiKey() {
        return new ApiKey(this.swaggerProperties.getAuthKey(), this.swaggerProperties.getAuthKey(), ApiKeyVehicle.HEADER.getValue());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.regex("^.*login.*$")).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(SecurityReference.builder().reference(this.swaggerProperties.getAuthKey()).scopes(authorizationScopes).build());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
