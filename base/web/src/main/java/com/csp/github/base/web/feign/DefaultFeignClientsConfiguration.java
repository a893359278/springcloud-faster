package com.csp.github.base.web.feign;

import com.csp.github.base.web.hystrix.DefaultHystrixFallbackHandlerFactory;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.converter.SpringManyMultipartFilesReader;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.DefaultHystrixFallbackFactory;
import org.springframework.cloud.openfeign.HystrixFallbackConfiguration;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 陈少平
 * @date 2019-12-06 22:41
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import({HystrixFallbackConfiguration.class})
public class DefaultFeignClientsConfiguration implements WebMvcConfigurer {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public DefaultHystrixFallbackFactory defaultHystrixFallbackFactory() {
        DefaultHystrixFallbackFactory defaultHystrixFallbackFactory = new DefaultHystrixFallbackFactory();
        defaultHystrixFallbackFactory.setFallbackHandlerFactory(new DefaultHystrixFallbackHandlerFactory());
        return defaultHystrixFallbackFactory;
    }

    @Resource
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder () {
        return new SpringEncoder(messageConverters);
    }

    @Bean
    public Decoder feignDecoder () {
        List<HttpMessageConverter<?>> springConverters =
                messageConverters.getObject().getConverters();
        List<HttpMessageConverter<?>> decoderConverters =
                new ArrayList<>(springConverters.size() + 1);

        decoderConverters.addAll(springConverters);
        decoderConverters.add(new SpringManyMultipartFilesReader(4096));

        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(decoderConverters);
        FeignJsonResultDecoder jsonResultDecoder = new FeignJsonResultDecoder();
        jsonResultDecoder.setDecoder(new SpringDecoder(() -> httpMessageConverters));
        return jsonResultDecoder;
    }
}
