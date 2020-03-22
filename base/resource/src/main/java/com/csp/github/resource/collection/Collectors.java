package com.csp.github.resource.collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.send.Sender;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 收集器
 * @author 陈少平
 * @date 2019-11-15 22:36
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ResourceProperties.class)
@ConditionalOnProperty(prefix = "resources.collector", value = "enable", havingValue = "true")
public class Collectors implements BeanPostProcessor, ApplicationContextAware, CommandLineRunner, EnvironmentAware {

    @Resource
    private ResourceProperties resourceProperties;

    private ApplicationContext ac;
    private List<Class> list = new ArrayList<>();

    private String threadName;

    private String contextPath;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<ResourceFilterStrategy> strategies = resourceProperties.getFilterList();
        strategies.forEach(strategy -> {
            if (strategy.filter(bean, beanName)) {
                list.add(bean.getClass());
            }
        });
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {

        Thread thread = new Thread(() -> {

            log.info("资源收集线程：{}, 开启", threadName);

            Set<ResourceEntity> array = new HashSet<>();
            Sender sender = resourceProperties.getSender();

            try {
                resourceProperties.getCollectionList()
                        .forEach(strategy -> array.addAll(strategy.collectionStrategy(this.list, contextPath)));

                for (ResourceEntity resourceEntity : array) {
                    sender.sendResources(JSON.toJSONString(resourceEntity));
                }

                log.info("收集到的资源：{}", JSONObject.toJSONString(array));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("收集资源失败！{}", e.getMessage());
            } finally {
                array.clear();
                log.info("资源收集线程：{}，结束", threadName);
            }

        }, threadName);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.threadName = environment.getProperty("spring.application.name", "collector-permission-thread");
        this.contextPath = environment.getProperty("server.servlet.contextPath", "/");
    }
}
