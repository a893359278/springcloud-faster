package com.csp.github.resource.send;

import com.csp.github.resource.collection.ResourceProperties;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 陈少平
 * @date 2020-03-22 16:34
 */
public class RedisSender implements Sender {

    public RedisSender(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    StringRedisTemplate redisTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public void sendResources(String msg) {
        ListOperations opt = redisTemplate.opsForList();
        opt.rightPush(ResourceProperties.DEFAULT_CHANNEL, msg);
    }
}
