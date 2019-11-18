package com.csp.github.resource.collection;

/**
 * 资源过滤策略
 * @author 陈少平
 * @date 2019-11-15 23:25
 */
@FunctionalInterface
public interface ResourceFilterStrategy {

    /**
     * 是否应该过滤
     * @param bean
     * @param name
     * @return
     */
    boolean filter(Object bean, String name);
}
