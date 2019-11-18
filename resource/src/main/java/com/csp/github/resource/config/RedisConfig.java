package com.csp.github.resource.config;

import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.config.ProtobufSerializer.SerializeWrap;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
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

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(new ProtobufSerializer<>());

        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(new ProtobufSerializer<>());

        template.afterPropertiesSet();
        return template;
    }

    public static void main(String[] args) {
        ResourceEntity resourceEntity = new ResourceEntity().setDescription("qwqwe").setVersion(1).setName("qweqw");
        Schema<ProtobufSerializer.SerializeWrap> schema = RuntimeSchema.createFrom(ProtobufSerializer.SerializeWrap.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte [] bytes = ProtobufIOUtil.toByteArray(new ProtobufSerializer.SerializeWrap(resourceEntity), schema, buffer);

        ProtobufSerializer.SerializeWrap warp = new ProtobufSerializer.SerializeWrap();

        ProtostuffIOUtil.mergeFrom(bytes, warp, schema);
        System.out.println(warp.getObj());
    }
}
