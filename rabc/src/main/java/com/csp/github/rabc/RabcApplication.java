package com.csp.github.rabc;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.csp.github.base.web.annotation.StarterApplication;
import com.csp.github.swagger.autoconfig.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 陈少平
 * @date 2019-11-16 15:02
 */
@EnableSwagger
@StarterApplication
@MapperScan("com.csp.github.rabc.mapper")
public class RabcApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RabcApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Bean
    public IKeyGenerator keyGenerator() {
        return new OracleKeyGenerator();
    }
}
