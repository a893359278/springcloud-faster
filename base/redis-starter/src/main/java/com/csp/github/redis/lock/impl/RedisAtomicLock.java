package com.csp.github.redis.lock.impl;

import com.alibaba.fastjson.JSON;
import com.csp.github.redis.lock.AtomicLock;
import com.csp.github.redis.lock.LockProperty;
import com.csp.github.redis.lock.LockType;
import com.csp.github.redis.lock.SerializationType;
import com.csp.github.redis.lock.exception.TryLockFailException;
import com.csp.github.redis.lock.exception.TryLockInterruptedException;
import com.csp.github.redis.protobuf.ProtobufRedisTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * redis 锁实现类
 * @author 陈少平
 * @date 2020-03-05 20:58
 */
public class RedisAtomicLock implements AtomicLock {

    private static String ATOMIC_DELETE_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private List<RedisSerializationAdapter> redisTemplates = new ArrayList<>();

    public void addRedisTemplateWrap(RedisSerializationAdapter redisTemplate) {
        redisTemplates.add(redisTemplate);
    }

    /**
     * 获取合适的 redis 序列化器
     * @param serializationType
     * @return
     */
    public RedisSerializationAdapter getSuitableRedisSerialization(SerializationType serializationType) {
        for (RedisSerializationAdapter redisTemplate : redisTemplates) {
            if (redisTemplate.support(serializationType)) {
                return redisTemplate;
            }
        }
        return null;
    }

    @Override
    public boolean support(LockType lockType) {
        return LockType.REDIS == lockType;
    }

    @Override
    public Object tryLock(String k, Object v, LockProperty lock, Supplier supplier) {

        int i = lock.getFailTryCount();
        do {

            if (getSuitableRedisSerialization(lock.getSerialization()).wrapSetIfAbsent(k, v, lock.getTime(), lock.getTimeUnit())) {
                return supplier.get();
            }

            try {
                long l = lock.getFailIntervalTimeUnit().toMillis(lock.getFailInterval());
                Thread.sleep(l);
            } catch (InterruptedException ex) {
                throw new TryLockInterruptedException();
            }
            i--;
        } while (i > 0);

        throw new TryLockFailException();
    }

    @Override
    public boolean unLock(String k, Object v, SerializationType serializationType) {
        RedisSerializationAdapter redisSerialization = getSuitableRedisSerialization(serializationType);
        RedisTemplate redisTemplate = redisSerialization.getRedisTemplate();
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(ATOMIC_DELETE_SCRIPT);
        redisScript.setResultType(Long.class);
        Object execute = redisTemplate.execute(redisScript, Collections.singletonList(k), v);
        return !Objects.equals(execute, 1);
    }


    public static class ProtobufRedisSerializationAdapter implements RedisSerializationAdapter<String, Object> {

        private ProtobufRedisTemplate redisTemplate;

        public ProtobufRedisSerializationAdapter(ProtobufRedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        public ProtobufRedisTemplate getRedisTemplate() {
            return redisTemplate;
        }

        @Override
        public boolean wrapSetIfAbsent(String k, Object v, long time, TimeUnit timeUnit) {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            Boolean success = ops.setIfAbsent(k, v, time, timeUnit);



            return Objects.equals(success, true);
        }

        @Override
        public boolean support(SerializationType type) {
            return SerializationType.PROTOBUF == type;
        }
    }

    public static class StringRedisSerializationAdapter implements RedisSerializationAdapter {

        private StringRedisTemplate redisTemplate;

        public StringRedisSerializationAdapter(StringRedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        public StringRedisTemplate getRedisTemplate() {
            return redisTemplate;
        }

        @Override
        public boolean wrapSetIfAbsent(String k, Object v, long time, TimeUnit timeUnit) {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            Boolean success = ops.setIfAbsent(k, JSON.toJSONString(v), time, timeUnit);
            return Objects.equals(success, true);
        }

        @Override
        public boolean support(SerializationType type) {
            return SerializationType.JSON == type;
        }
    }

    /**
     * redis 序列化适配器
     */
    public interface RedisSerializationAdapter<K, V> {
        boolean wrapSetIfAbsent(String k, Object v, long time, TimeUnit timeUnit);
        boolean support(SerializationType type);
        RedisTemplate<K, V> getRedisTemplate();
    }

}
