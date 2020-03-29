package com.csp.github.redis.lock.autoconfig;

import com.csp.github.redis.lock.LockType;
import com.csp.github.redis.lock.SerializationType;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * provide global configuration override Lock annotation
 * @author 陈少平
 * @date 2020-03-07 08:26
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "lock")
public class LockProperty implements Cloneable {

    private LockType type;

    private SerializationType serialization;

    private Long time;

    private TimeUnit timeUnit;

    private Integer failTryCount;

    private Long failInterval;

    private TimeUnit failIntervalTimeUnit;

    @Override
    public LockProperty clone() {
        LockProperty bean;
        try {
            bean = (LockProperty) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError("LockProperty clone fail " + ex.getMessage());
        }
        return bean;
    }

}
