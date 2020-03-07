package com.csp.github.redis.lock.autoconfig;

import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.redis.autoconfig.RedisConfig;
import com.csp.github.redis.lock.LockAspect;
import com.csp.github.redis.lock.impl.LocalAtomicLock;
import com.csp.github.redis.lock.impl.RedisAtomicLock;
import com.csp.github.redis.lock.impl.RedisAtomicLock.ProtobufRedisSerializationAdapter;
import com.csp.github.redis.lock.impl.RedisAtomicLock.StringRedisSerializationAdapter;
import com.csp.github.redis.protobuf.ProtobufRedisTemplate;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 陈少平
 * @date 2020-03-06 20:27
 */
@Configuration
@AutoConfigureAfter(RedisConfig.class)
@EnableConfigurationProperties(LockProperty.class)
public class LockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LocalAtomicLock localAtomicLock() {
        return new LocalAtomicLock();
    }

    @Bean
    @ConditionalOnClass(value = {StringRedisTemplate.class, ProtobufRedisTemplate.class})
    public RedisAtomicLock redisAtomicLock(StringRedisTemplate stringRedisTemplate, ProtobufRedisTemplate protobufRedisTemplate) {
        RedisAtomicLock redisAtomicLock = new RedisAtomicLock();
        ProtobufRedisSerializationAdapter protobufRedisSerializationAdapter = new ProtobufRedisSerializationAdapter(protobufRedisTemplate);
        StringRedisSerializationAdapter stringRedisSerializationAdapter = new StringRedisSerializationAdapter(stringRedisTemplate);

        redisAtomicLock.addRedisTemplateWrap(protobufRedisSerializationAdapter);
        redisAtomicLock.addRedisTemplateWrap(stringRedisSerializationAdapter);

        return redisAtomicLock;
    }

    @Bean
    public LockAspect lockAspect(ApplicationContext context) {
        LocalAtomicLock localAtomicLock = null;
        RedisAtomicLock redisAtomicLock = null;

        if (context.containsBean("redisAtomicLock")) {
            redisAtomicLock = context.getBean(RedisAtomicLock.class);
        }
        if (context.containsBean("localAtomicLock")) {
            localAtomicLock = context.getBean(LocalAtomicLock.class);
        }

        if (Objects.isNull(localAtomicLock) && Objects.isNull(redisAtomicLock)) {
            throw new ServiceException("Initialization LockAspect fail, required LocalAtomicLock or RedisAtomicLock.");
        }
        LockAspect lockAspect = new LockAspect();
        if (Objects.nonNull(localAtomicLock)) {
            lockAspect.addAtomicLock(localAtomicLock);
        }
        if (Objects.nonNull(redisAtomicLock)) {
            lockAspect.addAtomicLock(redisAtomicLock);
        }
        return lockAspect;
    }
}
