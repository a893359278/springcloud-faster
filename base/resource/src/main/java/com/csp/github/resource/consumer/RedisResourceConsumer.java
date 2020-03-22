package com.csp.github.resource.consumer;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.collection.ResourceProperties;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 陈少平
 * @date 2020-03-22 18:39
 */
public class RedisResourceConsumer implements ResourceConsumer {

    private BlockingQueue<String> msgBlockingQueue = new LinkedBlockingQueue<>();
    private ScheduledThreadPoolExecutor pullDataExecutor = new ScheduledThreadPoolExecutor(1);

    private StringRedisTemplate redisTemplate;

    public RedisResourceConsumer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        pullDataFromRedisServer();
    }

    private void pullDataFromRedisServer() {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        pullDataExecutor.scheduleWithFixedDelay(() -> {
            List<String> list = ops.range(ResourceProperties.DEFAULT_CHANNEL, 0, 100);
            if (CollectionUtil.isNotEmpty(list)) {
                msgBlockingQueue.addAll(list);
                ops.trim(ResourceProperties.DEFAULT_CHANNEL, list.size(), -1);
            }
        }, 10, 30, TimeUnit.SECONDS);
    }

    @Override
    public ResourceEntity pullResource() {
        try {
            return JSONObject.parseObject(msgBlockingQueue.take(), ResourceEntity.class);
        } catch (InterruptedException e) {
            return null;
        }
    }
}
