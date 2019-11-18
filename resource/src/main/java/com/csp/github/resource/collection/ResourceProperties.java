package com.csp.github.resource.collection;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties("resources.filter")
@Slf4j
@Accessors(chain = true)
public class ResourceProperties implements InitializingBean {


    public static final String DEFAULT_CHANNEL = "collection:resources";

    // 是否启用过滤器
    @Getter
    @Setter
    private boolean enable = true;

    // 是否启用默认的过滤器
    @Getter
    @Setter
    private boolean enableFilterDefault = true;
    // 是否启用默认的收集器
    @Getter
    @Setter
    private boolean enableCollectionDefault = true;
    // 允许自定义过滤策略
    @Getter
    @Setter
    private List<Class<ResourceFilterStrategy>> filterStrategies;
    // 允许自定义收集策略
    @Getter
    @Setter
    private List<Class<ResourceCollectionStrategy>> collectionStrategies;

    // 资源收集完毕，发送的通道
    @Getter
    @Setter
    private String sendChannel = DEFAULT_CHANNEL;

    @Getter
    private List<ResourceCollectionStrategy> collectionList = new ArrayList<>();
    @Getter
    private List<ResourceFilterStrategy> filterList = new ArrayList<>();

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
    }
}
