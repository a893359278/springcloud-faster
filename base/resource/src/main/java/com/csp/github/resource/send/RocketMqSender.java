package com.csp.github.resource.send;

import com.csp.github.resource.collection.ResourceProperties;
import javax.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author 陈少平
 * @date 2020-01-15 22:13
 */
@ConditionalOnMissingBean(value = RocketMqSender.class)
@Component
public class RocketMqSender implements Sender {

    @Resource
    ResourceProperties resourceProperties;

    @Lazy
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendResources(String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.send(resourceProperties.getSendChannel(), message);
    }
}
