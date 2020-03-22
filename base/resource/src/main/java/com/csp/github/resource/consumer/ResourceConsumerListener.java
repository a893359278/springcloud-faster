package com.csp.github.resource.consumer;

import com.csp.github.resource.annotation.ResourceEntity;

/**
 * @author 陈少平
 * @date 2020-03-22 17:32
 */
public interface ResourceConsumerListener {

    /**
     *
     * @param resourceEntity not null
     */
    void consume(ResourceEntity resourceEntity);
}
