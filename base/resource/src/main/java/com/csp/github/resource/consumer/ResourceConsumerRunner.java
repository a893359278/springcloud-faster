package com.csp.github.resource.consumer;

import cn.hutool.core.thread.ThreadUtil;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.collection.ResourceProperties;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈少平
 * @date 2020-03-22 17:16
 */
@ConditionalOnProperty(value = "resource.collector.consumerEnable",havingValue = "true")
@Configuration
public class ResourceConsumerRunner implements CommandLineRunner {

    @Resource
    ResourceProperties resourceProperties;

    @Override
    public void run(String... args) throws Exception {
        if (resourceProperties.isConsumerEnable()) {
            ResourceConsumer consumer = resourceProperties.getConsumer();
            ResourceConsumeListener consumeListener = resourceProperties.getConsumeListener();
            Thread t = new Thread(() -> {
                long time = 1;
                while (true) {
                    ResourceEntity resourceEntity = consumer.pullResource();
                    if (Objects.nonNull(resourceEntity)) {
                        consumeListener.consume(resourceEntity);
                    } else {
                        ThreadUtil.sleep((time * 60) % Long.MAX_VALUE, TimeUnit.SECONDS);
                    }
                }
            });
            t.start();
        }
    }
}
