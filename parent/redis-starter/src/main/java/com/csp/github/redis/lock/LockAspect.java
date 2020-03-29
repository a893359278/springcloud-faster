package com.csp.github.redis.lock;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.redis.lock.autoconfig.LockProperty;
import com.csp.github.redis.lock.exception.NotFoundAtomicLock;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * @author 陈少平
 * @date 2020-03-05 20:40
 */
@Aspect
public class LockAspect {

    @Resource
    LockProperty lockProperty;

    @Pointcut("@annotation(Lock)")
    public void atomicLock(){}

    @Around("atomicLock()")
    public Object lockAspect(ProceedingJoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Lock lockAnnotation = method.getAnnotation(Lock.class);
        LockProperty property = shouldReplaceLockConfig(lockAnnotation, lockProperty);
        AtomicLock lock = getAtomicLock(property.getType());
        String key = getLockKey(method, point.getArgs());
        String value = getLockValue();
        try {
            return lock.tryLock(key, value, property, () -> {
                try {
                    return point.proceed();
                } catch (Throwable throwable) {
                    throw new ServiceException(throwable.getMessage());
                }
            });
        } finally {
            lock.unLock(key, value, lockAnnotation.serialization());
        }
    }

    /**
     * using LockProperty replace Lock config
     * when LockProperty has value, then using it replace @Lock config
     *
     * lock config order priority, from low to high
     *
     * 1. @Lock config
     * 2. global LockProperty config
     *
     * @param lockAnnotation
     * @param property
     * @return property clone()
     */
    private LockProperty shouldReplaceLockConfig(Lock lockAnnotation, LockProperty property) {
        LockProperty clone = property.clone();
        boolean override = lockAnnotation.override();
        if (override) {
            if (Objects.isNull(clone.getType())) {
                clone.setType(lockAnnotation.type());
            }
            if (Objects.isNull(clone.getTimeUnit())) {
                clone.setTimeUnit(lockAnnotation.timeUnit());
            }
            if (Objects.isNull(clone.getTime())) {
                clone.setTime(lockAnnotation.time());
            }
            if (Objects.isNull(clone.getFailTryCount())) {
                clone.setFailTryCount(lockAnnotation.failTryCount());
            }
            if (Objects.isNull(clone.getFailInterval())) {
                clone.setFailInterval(lockAnnotation.failInterval());
            }
            if (Objects.isNull(clone.getFailIntervalTimeUnit())) {
                clone.setFailIntervalTimeUnit(lockAnnotation.failIntervalTimeUnit());
            }
            if (Objects.isNull(clone.getSerialization())) {
                clone.setSerialization(lockAnnotation.serialization());
            }
        } else {
            lockAnnotation2LockProperty(clone, lockAnnotation);
        }
        return clone;
    }

    private void lockAnnotation2LockProperty(LockProperty clone, Lock lockAnnotation) {
        clone.setType(lockAnnotation.type());
        clone.setTimeUnit(lockAnnotation.timeUnit());
        clone.setTime(lockAnnotation.time());
        clone.setFailTryCount(lockAnnotation.failTryCount());
        clone.setFailInterval(lockAnnotation.failInterval());
        clone.setFailIntervalTimeUnit(lockAnnotation.failIntervalTimeUnit());
        clone.setSerialization(lockAnnotation.serialization());
    }

    private String getLockValue() {
        return RandomUtil.randomNumbers(6);
    }

    private String getLockKey(Method method, Object[] args) {
        return method.getDeclaringClass().getSimpleName() + ":" + method.getName() + ":" + JSON.toJSONString(args);
    }

    private List<AtomicLock> locks = new ArrayList<>();

    public void addAtomicLock(AtomicLock lock) {
        locks.add(lock);
    }

    public AtomicLock getAtomicLock(LockType lockType) {
        for (AtomicLock lock : locks) {
            if (lock.support(lockType)) {
                return lock;
            }
        }
        throw new NotFoundAtomicLock("not found "+ lockType.name() +" lock, mark sure it had initialization");
    }

}
