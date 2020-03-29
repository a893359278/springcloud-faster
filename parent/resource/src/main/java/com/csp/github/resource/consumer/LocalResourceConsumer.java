package com.csp.github.resource.consumer;

import com.alibaba.fastjson.JSONObject;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.send.DataCenter;

/**
 * @author 陈少平
 * @date 2020-03-22 17:25
 */
public class LocalResourceConsumer implements ResourceConsumer {

    @Override
    public ResourceEntity pullResource() {
        String resource = DataCenter.getResource();
        return JSONObject.parseObject(resource, ResourceEntity.class);
    }
}
