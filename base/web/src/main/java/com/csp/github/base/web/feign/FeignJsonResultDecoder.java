package com.csp.github.base.web.feign;

import com.alibaba.fastjson.JSONObject;
import com.csp.github.base.common.entity.Result;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import javax.security.sasl.SaslException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 解码上游 api 传过来的 json 对象。
 * @author 陈少平
 * @date 2019-12-09 21:29
 */
public class FeignJsonResultDecoder implements Decoder {


    private Decoder decoder;

    public Decoder getDecoder() {
        return decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(final Response response, Type type)
            throws IOException, FeignException {
        System.out.println(type.getTypeName());

        if (isParameterizeHttpEntity(type)) {
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            Object decodedObject = this.decoder.decode(response, type);

            return createResponse(decodedObject, response);
        }
        else if (isHttpEntity(type)) {
            return createResponse(null, response);
        }
        else {
            Object result = this.decoder.decode(response, String.class);
            Class cls = null;
            try {
                cls = Class.forName(type.getTypeName());
            } catch (ClassNotFoundException e) {
                throw new SaslException();
            }
            return JSONObject.parseObject(JSONObject.toJSONString(JSONObject.parseObject(result.toString(), Result.class).getData()), cls);
        }
    }

    private boolean isParameterizeHttpEntity(Type type) {
        if (type instanceof ParameterizedType) {
            return isHttpEntity(((ParameterizedType) type).getRawType());
        }
        return false;
    }

    private boolean isHttpEntity(Type type) {
        if (type instanceof Class) {
            Class c = (Class) type;
            return HttpEntity.class.isAssignableFrom(c);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponse(Object instance, Response response) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        for (String key : response.headers().keySet()) {
            headers.put(key, new LinkedList<>(response.headers().get(key)));
        }

        return new ResponseEntity<>((T) instance, headers,
                HttpStatus.valueOf(response.status()));
    }


}
