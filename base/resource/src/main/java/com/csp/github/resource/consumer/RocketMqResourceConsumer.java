package com.csp.github.resource.consumer;

import com.alibaba.fastjson.JSONObject;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.collection.ResourceProperties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author 陈少平
 * @date 2020-03-22 18:38
 */
@RocketMQMessageListener(topic = ResourceProperties.DEFAULT_CHANNEL, consumerGroup = "collectionResourcesConsume")
@ConditionalOnProperty(value = "resources.collector.send", havingValue = "rocketMQ")
@Component
public class RocketMqResourceConsumer implements ResourceConsumer, RocketMQListener<String> {

    private BlockingQueue<String> msgBlockingQueue = new LinkedBlockingQueue<>();

    @Override
    public ResourceEntity pullResource() {
        try {
            return JSONObject.parseObject(msgBlockingQueue.take(), ResourceEntity.class);
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public void onMessage(String o) {
        msgBlockingQueue.add(o);
    }
}
