package com.csp.github.tenant;

import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.consumer.ResourceConsumerListener;

/**
 * @author 陈少平
 * @date 2020-03-22 22:58
 */
public class ConsumerTest implements ResourceConsumerListener {

    @Override
    public void consume(ResourceEntity resourceEntity) {
        System.out.println(resourceEntity);
    }
}
