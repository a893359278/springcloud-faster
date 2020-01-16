package com.csp.github.redis.protobuf;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
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

    private static final Schema<SerializeWrap> SERIALIZE_WRAP_SCHEMA = RuntimeSchema.getSchema(SerializeWrap.class);

    @Override
    public byte[] serialize(Object t) throws SerializationException {

        if (t == null) {
            return new byte[0];
        }

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        final byte[] bytes;

        try {
            SerializeWrap wrap = new SerializeWrap();
            wrap.setObj(t);
            bytes = ProtostuffIOUtil.toByteArray(wrap, SERIALIZE_WRAP_SCHEMA, buffer);
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
            SerializeWrap wrap = SERIALIZE_WRAP_SCHEMA.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, wrap, SERIALIZE_WRAP_SCHEMA);
            return (T) wrap.getObj();
        } else {
            return null;
        }
    }

    public static class SerializeWrap {
        private Object obj;

        public SerializeWrap() {
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
