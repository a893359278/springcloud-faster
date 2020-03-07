package com.csp.github.redis.lock.impl;


import com.csp.github.redis.lock.AtomicLock;
import com.csp.github.redis.lock.LockProperty;
import com.csp.github.redis.lock.LockType;
import com.csp.github.redis.lock.SerializationType;
import com.csp.github.redis.lock.exception.TryLockFailException;
import com.csp.github.redis.lock.exception.TryLockInterruptedException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 单机环境下的本地锁
 * @author 陈少平
 * @date 2020-03-06 19:02
 */
public class LocalAtomicLock implements AtomicLock {

    private final ConcurrentHashMap<String, Object> map;

    private final ScheduledThreadPoolExecutor threadPoolExecutor;

    public LocalAtomicLock() {
        this.map = new ConcurrentHashMap<>(16);
        this.threadPoolExecutor = new ScheduledThreadPoolExecutor(5, new ThreadFactory() {

            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "local unlock thread "+ threadIndex.incrementAndGet());
            }
        });
    }

    public LocalAtomicLock(ConcurrentHashMap<String, Object> map, ScheduledThreadPoolExecutor threadPoolExecutor) {
        this.map = map;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public boolean support(LockType lockType) {
        return LockType.LOCAL == lockType;
    }

    @Override
    public Object tryLock(String k, Object v, LockProperty lock, Supplier supplier) {
        int count = lock.getFailTryCount();
        do {
            Object val = map.putIfAbsent(k, v);
            if (Objects.isNull(val)) {
                threadPoolExecutor.schedule(() -> {
                    map.remove(k, v);
                }, lock.getTime(), lock.getTimeUnit());
                return supplier.get();
            } else {
                try {
                    Thread.sleep(lock.getFailIntervalTimeUnit().toMillis(lock.getFailInterval()));
                } catch (InterruptedException ex) {
                    throw new TryLockInterruptedException();
                } finally {
                    map.remove(k, v);
                }
            }
            count--;
        } while (count > 0);
        throw new TryLockFailException();
    }

    @Override
    public boolean unLock(String k, Object v, SerializationType serializationType) {
        return map.remove(k, v);
    }
}
