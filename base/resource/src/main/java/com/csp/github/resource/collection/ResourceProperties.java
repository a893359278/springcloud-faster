package com.csp.github.resource.collection;

import com.csp.github.base.common.exception.InternalException;
import com.csp.github.resource.consumer.LocalResourceConsumer;
import com.csp.github.resource.consumer.RedisResourceConsumer;
import com.csp.github.resource.consumer.ResourceConsumerListener;
import com.csp.github.resource.consumer.ResourceConsumer;
import com.csp.github.resource.consumer.RocketMqResourceConsumer;
import com.csp.github.resource.send.LocalSender;
import com.csp.github.resource.send.RedisSender;
import com.csp.github.resource.send.RocketMqSender;
import com.csp.github.resource.send.SendEnum;
import com.csp.github.resource.send.Sender;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * 资源收集器属性
 *
 * 默认启用 SwaggerResourceCollectionStrategy 收集器
 * 默认启用 RestResourceFilterDefaultStrategy 过滤器
 *
 * @author 陈少平
 * @date 2019-11-15 23:35
 */
@ConfigurationProperties("resources.collector")
@Slf4j
@Accessors(chain = true)
public class ResourceProperties implements InitializingBean, ApplicationContextAware {


    public static final String DEFAULT_CHANNEL = "collectionResources";

    // 是否启用过滤器
    @Getter
    @Setter
    private boolean enable = true;

    // enable consumer or not
    @Getter
    @Setter
    private boolean consumerEnable = false;

    // 是否启用默认的过滤器
    @Getter
    @Setter
    private boolean enableFilterDefault = true;
    // 是否启用默认的收集器
    @Getter
    @Setter
    private boolean enableCollectionDefault = true;

    @Getter
    @Setter
    private SendEnum send = SendEnum.redis;

    @Getter
    @Setter
    private Sender sender;

    @Getter
    @Setter
    private ResourceConsumer consumer;

    // implement the class and write your consumed logic
    @Getter
    @Setter
    private Class<? extends ResourceConsumerListener> consumerListenerCls;

    @Getter
    private ResourceConsumerListener consumerListener;

    // 允许自定义过滤策略
    @Getter
    @Setter
    private List<Class<ResourceFilterStrategy>> filterStrategies;
    // 允许自定义收集策略
    @Getter
    @Setter
    private List<Class<ResourceCollectionStrategy>> collectionStrategies;

    @Getter
    private List<ResourceCollectionStrategy> collectionList = new ArrayList<>();
    @Getter
    private List<ResourceFilterStrategy> filterList = new ArrayList<>();

    private ApplicationContext ac;

    /**
     * 初始化收集器
     */
    private void initCollectionStrategy() {
        if (CollectionUtils.isEmpty(collectionStrategies)) {
            collectionList.add(new SwaggerResourceCollectionStrategy());
        } else {
            collectionStrategies.forEach(cls -> {
                try {
                    ResourceCollectionStrategy strategy = cls.newInstance();
                    collectionList.add(strategy);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("初始化资源收集器失败：{}", e.getMessage());
                }
            });
            if (enableCollectionDefault) {
                collectionList.add(new SwaggerResourceCollectionStrategy());
            }
        }
    }

    /**
     * 初始化过滤器
     */
    private void initFilterStrategy() {
        if (CollectionUtils.isEmpty(filterStrategies)) {
            filterList.add(new RestResourceFilterDefaultStrategy());
        } else {
            filterStrategies.forEach(cls -> {
                try {
                    ResourceFilterStrategy strategy = cls.newInstance();
                    filterList.add(strategy);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("初始化资源过滤器失败：{}", e.getMessage());
                }
            });
            if (enableFilterDefault) {
                filterList.add(new RestResourceFilterDefaultStrategy());
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFilterStrategy();
        initCollectionStrategy();
        initSender();
        if (consumerEnable) {
            initConsumer();
        }
    }

    private void initConsumer() {
        if (Objects.nonNull(this.consumer)) {
            return ;
        }
        if (Objects.isNull(consumerListenerCls)) {
            throw new InternalException("because consumerEnable is true,so consumerListener must not null");
        } else {
            try {
                consumerListener = consumerListenerCls.newInstance();
            } catch (Exception e) {}
        }

        switch (this.send) {
            case rocketMQ:
                RocketMqResourceConsumer bean = ac.getBean(RocketMqResourceConsumer.class);
                if (Objects.isNull(bean)) {
                    throw new InternalException("initialization consumer fail, because RocketMqConsumer is null");
                }
                this.consumer = bean;
                break;
            case local:
                this.consumer = new LocalResourceConsumer();
                break;
            case redis:
                StringRedisTemplate redisTemplate = ac.getBean(StringRedisTemplate.class);
                if (Objects.isNull(redisTemplate)) {
                    throw new InternalException( "initialization consumer fail, because StringRedisTemplate is null");
                }
                this.consumer = new RedisResourceConsumer(redisTemplate);
                break;
            default:
                throw new InternalException("initialization sender fail, not found sendEnum：" +  send.name() + " impl");
        }
    }

    private void initSender() {

        if (Objects.nonNull(this.sender)) {
            return ;
        }
        if (Objects.isNull(send)) {
            throw new NullPointerException("initialization sender fail, because send is null.");
        }

        switch (this.send) {
            case rocketMQ:
                RocketMQTemplate rocketMQTemplate = ac.getBean(RocketMQTemplate.class);
                this.sender = new RocketMqSender(rocketMQTemplate);
                break;
            case redis:
                StringRedisTemplate redisTemplate = ac.getBean(StringRedisTemplate.class);
                this.sender = new RedisSender(redisTemplate);
                break;
            case local:
                this.sender = new LocalSender();
                break;
            default:
                throw new InternalException("initialization sender fail, not found sendEnum：" +  send.name() + " impl");
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }
}
