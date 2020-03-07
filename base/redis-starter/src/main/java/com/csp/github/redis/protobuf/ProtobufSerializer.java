package com.csp.github.redis.protobuf;


import java.util.Objects;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis protobuf 序列化接口
 *
 * @author 陈少平
 * @date 2019-11-16 11:52
 */
public class ProtobufSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object t) throws SerializationException {
       return ProtobufSerializerUtils.serialize(t);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return ProtobufSerializerUtils.deserialize(bytes);
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
