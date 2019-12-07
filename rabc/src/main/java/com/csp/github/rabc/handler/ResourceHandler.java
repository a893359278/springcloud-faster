package com.csp.github.rabc.handler;

import com.csp.github.base.common.utils.ConvertUtils;
import com.csp.github.resource.collection.Collectors;
import com.csp.github.resource.collection.ResourceProperties;
import com.csp.github.resource.protobuf.ProtobufMessageListenerAdapter;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author 陈少平
 * @date 2019-11-16 15:02
 */
@Slf4j
@Configuration
@AutoConfigureAfter(Collectors.class)
public class ResourceHandler {

    @Resource
    /**
     * 默认的资源处理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "resourceCollectionListener")
    ProtobufMessageListenerAdapter resourceCollectionListener() {
        return new ProtobufMessageListenerAdapter((MessageListener) (message, pattern) -> {
            if (ConvertUtils.bytes2Str(pattern).equals(ResourceProperties.DEFAULT_CHANNEL)) {
                log.info("资源：{}", message.toString());
            }
        });
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(resourceCollectionListener(), new ChannelTopic(ResourceProperties.DEFAULT_CHANNEL));
        return container;
    }
}
