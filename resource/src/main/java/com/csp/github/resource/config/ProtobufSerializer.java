package com.csp.github.resource.config;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import java.util.Objects;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis protobuf 序列化接口
 *
 * @author 陈少平
 * @date 2019-11-16 11:52
 */
public class ProtobufSerializer<T> implements RedisSerializer<T> {

    private static final Schema<SerializeWrap> SERIALIZE_FADE_SCHEMA = RuntimeSchema.getSchema(SerializeWrap.class);

    @Override
    public byte[] serialize(Object t) throws SerializationException {

        if (t == null) {
            return new byte[0];
        }

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] bytes;

        try {
            bytes = ProtobufIOUtil.toByteArray(new SerializeWrap(t), SERIALIZE_FADE_SCHEMA, buffer);
        } catch (Exception e) {
            throw new SerializationException("Cannot serialize" + e.getMessage(), e);
        } finally {
            buffer.clear();
        }

        return bytes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length != 0) {
            SerializeWrap warp = new SerializeWrap();
            ProtostuffIOUtil.mergeFrom(bytes, warp, SERIALIZE_FADE_SCHEMA);
            return (T) warp.getObj();
        } else {
            return null;
        }
    }

    public static class SerializeWrap {
        private Object obj;

        public SerializeWrap() {
        }

        public SerializeWrap(Object obj) {
            this.obj = obj;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SerializeWrap)) {
                return false;
            }
            SerializeWrap that = (SerializeWrap) o;
            return Objects.equals(getObj(), that.getObj());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getObj());
        }

        @Override
        public String toString() {
            return "SerializeWrap{" +
                    "obj=" + obj +
                    '}';
        }
    }
}
