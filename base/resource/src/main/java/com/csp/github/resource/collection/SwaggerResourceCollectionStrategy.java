package com.csp.github.resource.collection;

import com.csp.github.resource.annotation.ResourceEntity;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.util.StringUtils;

/**
 * swagger API 收集器
 * swagger     resource
 *  value   =>    name
 *  notes   =>   {version}<=>{description}。 例如： 2<=>请问企鹅无请问请问
 * @author 陈少平
 * @date 2019-11-16 15:37
 */
public class SwaggerResourceCollectionStrategy extends AbstractResourceCollectionStrategy{

    public static final String SPLIT = "<=>";

    @Override
    protected boolean fillResourceEntityAttributions(ResourceEntity entity, Method method) {
        ApiOperation r = method.getAnnotation(ApiOperation.class);
        if (Objects.isNull(r)) {
            return false;
        }
        String value = r.value();
        entity.setName(value);
        String notes = r.notes();
        if (StringUtils.isEmpty(notes)) {
            entity.setDescription(value);
        } else {
            String[] split = notes.split(SPLIT);
            if (split.length == 2) {
                entity.setVersion(Integer.parseInt(split[0]));
                entity.setDescription(split[1]);
            }
            if (split.length == 1) {
                try {
                    entity.setVersion(Integer.parseInt(split[0]));
                } catch (NumberFormatException ex) {
                    entity.setDescription(split[0]);
                }
            }
        }

        return true;
    }
}
