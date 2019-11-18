package com.csp.github.resource.config;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
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

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
