package com.csp.github.resource.collection;

import com.csp.github.resource.annotation.ResourceCollection;
import com.csp.github.resource.annotation.ResourceEntity;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 资源收集器, 如需扩展，只需要继承 AbstractResourceCollectionStrategy 即可
 * @author 陈少平
 * @date 2019-11-16 10:08
 */
public class ResourceCollectionDefaultStrategy extends AbstractResourceCollectionStrategy {

    @Override
    protected boolean fillResourceEntityAttributions(ResourceEntity entity, Method method) {
        ResourceCollection r = method.getAnnotation(ResourceCollection.class);
        if (Objects.isNull(r)) {
            return false;
        }
        entity.setExtra(r.extra())
                .setDescription(r.description())
                .setVersion(r.version())
                .setName(r.name());
        return true;
    }


}
