package com.csp.github.resource.config;

import com.csp.github.resource.protobuf.ProtobufRedisTemplate;
import com.csp.github.resource.protobuf.ProtobufSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 陈少平
 * @date 2019-11-16 10:59
 */
@Configuration
@ConditionalOnMissingBean(name = "redisConfig")
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(serializer);

        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        redisTemplate.setStringSerializer(serializer);

        //开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtobufRedisTemplate protobufRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        ProtobufRedisTemplate template = new ProtobufRedisTemplate(redisConnectionFactory);
        ProtobufSerializer<Object> serializer = new ProtobufSerializer<>();
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}

