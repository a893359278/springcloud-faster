package com.csp.github.redis.protobuf;

import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * protobuf 消息监听适配器
 * @author 陈少平
 * @date 2019-11-20 22:15
 */
public class ProtobufMessageListenerAdapter extends MessageListenerAdapter {

    @Override
    protected void initDefaultStrategies() {
        setSerializer(new ProtobufSerializer<>());
        setStringSerializer(new ProtobufSerializer<>());
    }

    public ProtobufMessageListenerAdapter() {
        super();
    }

    public ProtobufMessageListenerAdapter(Object delegate) {
        super(delegate);
    }

    public ProtobufMessageListenerAdapter(Object delegate, String defaultListenerMethod) {
        super(delegate, defaultListenerMethod);
    }
}
