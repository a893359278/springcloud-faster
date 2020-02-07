package com.csp.github.base.web.autoconfig;

import com.csp.github.base.web.advice.RestResultResponseAdvice;
import com.csp.github.base.web.exception.DefaultGlobalExceptionHandlerAdvice;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
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
public class WebStarterAutoConfiguration {

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}
