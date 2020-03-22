package com.csp.github.resource.send;

import com.csp.github.resource.collection.ResourceProperties;
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 陈少平
 * @date 2020-03-22 16:34
 */
@ConditionalOnMissingBean(RedisSender.class)
@Component
public class RedisSender implements Sender {

    @Lazy
    @Resource(name = "stringRedisTemplate")
    RedisTemplate redisTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public void sendResources(String msg) {
        ListOperations opt = redisTemplate.opsForList();
        opt.rightPush(ResourceProperties.DEFAULT_CHANNEL, msg);
    }
}
