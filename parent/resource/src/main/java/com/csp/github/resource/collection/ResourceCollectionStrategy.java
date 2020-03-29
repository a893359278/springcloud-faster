package com.csp.github.resource.collection;

import com.csp.github.resource.annotation.ResourceEntity;
import java.util.List;
import java.util.Set;

/**
 * 资源收集策略
 * @author 陈少平
 * @date 2019-11-15 23:21
 */
@FunctionalInterface
public interface ResourceCollectionStrategy {

    /**
     * define how collect strategy
     * @see com.csp.github.resource.collection.ResourceFilterStrategy#filter(Object, String)
     * @param targets all cls that satisfied collection condition.
     * @param contextPath application context path
     * @return all resource
     */
    Set<ResourceEntity> collectionStrategy(List<Class> targets, String contextPath);
}
