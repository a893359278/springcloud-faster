package com.csp.github.resource.send;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author 陈少平
 * @date 2020-03-22 16:54
 */
@ConditionalOnMissingBean(LocalSender.class)
@Component
public class LocalSender implements Sender {

    @Override
    public void sendResources(String msg) {
        DataCenter.addResource(msg);
    }
}
