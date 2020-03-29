package com.csp.github.resource.consumer;

import cn.hutool.core.thread.ThreadUtil;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.collection.Collectors;
import com.csp.github.resource.collection.ResourceProperties;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈少平
 * @date 2020-03-22 17:16
 */
@AutoConfigureAfter(Collectors.class)
@ConditionalOnProperty(prefix = "resources.collector", value = "consumer-enable", havingValue = "true")
@Configuration
public class ResourceConsumerRunner implements CommandLineRunner {

    @Resource
    ResourceProperties resourceProperties;

    @Override
    public void run(String... args) throws Exception {
        if (resourceProperties.isConsumerEnable()) {
            ResourceConsumer consumer = resourceProperties.getConsumer();
            ResourceConsumerListener consumeListener = resourceProperties.getConsumerListener();
            Thread t = new Thread(() -> {
                while (true) {
                    ResourceEntity resourceEntity = consumer.pullResource();
                    if (Objects.nonNull(resourceEntity)) {
                        consumeListener.consume(resourceEntity);
                    } else {
                        ThreadUtil.sleep(30, TimeUnit.SECONDS);
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }
}
