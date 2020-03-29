package com.csp.github.resource.annotation;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 资源实体
 * @author 陈少平
 * @date 2019-11-15 23:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class ResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String url;
    private String method;
    private String description;
    private String extra;
    private int version = 1;
}
