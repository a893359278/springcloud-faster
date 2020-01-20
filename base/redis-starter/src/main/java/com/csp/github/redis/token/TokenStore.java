package com.csp.github.redis.token;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author 陈少平
 * @date 2020-01-19 11:01
 */
public class TokenStore {

    public static final String TENANT_TOKEN_REFRESH_STORE_PRE = "tenant:token:refresh:";
    public static final String TENANT_TOKEN_EXPIRE_STORE_PRE = "tenant:token:expire:";
    public static final String TENANT_CURRENT_ACCOUNT_STORE_PRE = "tenant:current:token:";
    public static final String TENANT_STORE_PRE = "tenant:info:";
    public static final String TENANT_PERMISSION_STORE_PRE = "tenant:permissions:";

    public static final long refreshMinutes = 120L;
    public static final long expireMinutes = 10080L;

    private StringRedisTemplate redisTemplate;

    public TokenStore(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveTenantInfo(Long id, String token, List permissionList, Object tenant) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // token 刷新
        ops.set(TENANT_TOKEN_REFRESH_STORE_PRE + token, id.toString(), refreshMinutes, TimeUnit.MINUTES);
        // token 过期
        ops.set(TENANT_TOKEN_EXPIRE_STORE_PRE + token, id.toString(), expireMinutes, TimeUnit.MINUTES);
        // 当前登录的账号 token
        ops.set(TENANT_CURRENT_ACCOUNT_STORE_PRE + id, token);
        // 当前账号信息
        ops.set(TENANT_STORE_PRE + id, JSON.toJSONString(tenant));
        if (CollectionUtil.isNotEmpty(permissionList)) {
            // 权限
            ops.set(TENANT_PERMISSION_STORE_PRE + id, JSON.toJSONString(permissionList));
        }
    }

    public Long getTenantRefreshToken(String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String id = ops.get(TENANT_TOKEN_REFRESH_STORE_PRE + token);
        if (Objects.nonNull(id)) {
            return Long.parseLong(id);
        } else {
            return null;
        }
    }

    public Long getTenantExpireToken(String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String id = ops.get(TENANT_TOKEN_EXPIRE_STORE_PRE + token);
        if (Objects.nonNull(id)) {
            return Long.parseLong(id);
        } else {
            return null;
        }
    }


    public String generatorUUIDToken() {
        return IdUtil.fastSimpleUUID();
    }

    public void refreshTenantToken(String oldToken, String newToken, Long tenantId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // token 刷新
        ops.set(TENANT_TOKEN_REFRESH_STORE_PRE + newToken, tenantId.toString(), refreshMinutes, TimeUnit.MINUTES);
        // 删除旧 token
        redisTemplate.delete(TENANT_TOKEN_EXPIRE_STORE_PRE + oldToken);
        // 设置新 token
        ops.set(TENANT_TOKEN_EXPIRE_STORE_PRE + newToken, tenantId.toString(), expireMinutes, TimeUnit.MINUTES);
        // 设置当前用户
        ops.set(TENANT_CURRENT_ACCOUNT_STORE_PRE + tenantId , newToken);
    }


    public List<String> getTenantPermission(Long tenantId) {
        String permissions = redisTemplate.opsForValue().get(TENANT_PERMISSION_STORE_PRE + tenantId);
        if (StrUtil.isNotBlank(permissions)) {
            return JSON.parseArray(permissions, String.class);
        } else {
            return new ArrayList<>();
        }
    }
}
