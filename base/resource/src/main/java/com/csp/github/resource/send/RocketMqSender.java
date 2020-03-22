package com.csp.github.resource.send;

import com.csp.github.resource.collection.ResourceProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author 陈少平
 * @date 2020-01-15 22:13
 */
public class RocketMqSender implements Sender {

    public RocketMqSender(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendResources(String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.send(ResourceProperties.DEFAULT_CHANNEL, message);
    }
}
