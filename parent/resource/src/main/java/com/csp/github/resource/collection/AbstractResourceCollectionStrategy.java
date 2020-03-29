package com.csp.github.resource.collection;

import com.csp.github.resource.annotation.ResourceEntity;
import com.csp.github.resource.handler.DeleteMappingHandlerAdapter;
import com.csp.github.resource.handler.GetMappingHandlerAdapter;
import com.csp.github.resource.handler.MappingHandlerAdapter;
import com.csp.github.resource.handler.PostMappingHandlerAdapter;
import com.csp.github.resource.handler.PutMappingHandlerAdapter;
import com.csp.github.resource.handler.RequestMappingHandlerAdapter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 陈少平
 * @date 2019-11-16 17:40
 */
public abstract class AbstractResourceCollectionStrategy implements ResourceCollectionStrategy{

    @Override
    public Set<ResourceEntity> collectionStrategy(List<Class> targets, String contextPath) {

        Set<ResourceEntity> set = new HashSet<>();
        for (Class cls : targets) {
            Annotation annotation = cls.getAnnotation(RequestMapping.class);
            String basePath = "";
            if (Objects.nonNull(annotation)) {
                RequestMapping tmp = (RequestMapping) annotation;
                String[] value = tmp.value();
                if (value.length > 0) {
                    basePath = value[0];
                }
            }
            final String tmpPath = basePath;
            Method[] methods = cls.getDeclaredMethods();
            if (methods.length > 0) {
                List<MappingHandlerAdapter> adapters = initMappingHandler();
                Stream.of(methods).forEach(method -> {
                    Annotation[] annotations = method.getDeclaredAnnotations();
                    Stream.of(annotations).forEach(an -> adapters.forEach(adapter -> {
                        if (adapter.adapter(an)) {
                            handler(set, adapter, method, contextPath, tmpPath);
                        }
                    }));
                });
            }
        }
        return set;
    }

    private void handler(Set<ResourceEntity> set, MappingHandlerAdapter adapter, Method method, String contextPath, String basePath) {
        String[] path = adapter.path();
        if (path.length > 0) {
            for (String s : path) {
                ResourceEntity entity = new ResourceEntity();
                entity.setMethod(adapter.method());
                entity.setUrl(Paths.get(contextPath, basePath, s).toString());
                if (fillResourceEntityAttributions(entity, method)) {
                    set.add(entity);
                }
            }
        } else {
            ResourceEntity entity = new ResourceEntity();
            entity.setMethod(adapter.method());
            entity.setUrl(Paths.get(contextPath, basePath).toString());
            if (fillResourceEntityAttributions(entity, method)) {
                set.add(entity);
            }
        }
    }

    /**
     * 资源填充
     * 默认已填充 method, url ， 请填充 resourceEntity 的其他属性
     * @param entity 需要被填充的实体
     * @param method
     * @return true：实体被填充； false：实体没被填充
     */
    protected abstract boolean fillResourceEntityAttributions(ResourceEntity entity, Method method);

    private List<MappingHandlerAdapter> initMappingHandler() {
        List<MappingHandlerAdapter> list = new ArrayList<>();
        list.add(new GetMappingHandlerAdapter());
        list.add(new PostMappingHandlerAdapter());
        list.add(new PutMappingHandlerAdapter());
        list.add(new DeleteMappingHandlerAdapter());
        list.add(new RequestMappingHandlerAdapter());
        return list;
    }
}
