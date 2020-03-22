package com.csp.github.resource.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.collection.ResourceProperties;
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
            String data = ops.leftPop(ResourceProperties.DEFAULT_CHANNEL);
            if (StrUtil.isNotBlank(data)) {
                msgBlockingQueue.add(data);
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
