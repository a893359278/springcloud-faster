package com.csp.github.redis.protobuf;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义 redisTemplate
 * @author 陈少平
 * @date 2019-11-16 23:53
 */
public class ProtobufRedisTemplate extends RedisTemplate<String, Object> {

    public ProtobufRedisTemplate(){}

    public ProtobufRedisTemplate(RedisConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
