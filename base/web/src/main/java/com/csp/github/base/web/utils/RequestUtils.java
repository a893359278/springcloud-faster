package com.csp.github.base.web.utils;

import cn.hutool.core.util.StrUtil;
import com.csp.github.base.common.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 陈少平
 * @date 2020-01-21 20:01
 */
public class RequestUtils {

    public static Long getTenantId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader("x-tenant-id");
        if (StrUtil.isBlank(header)) {
            throw new ServiceException("tenantId 不存在");
        }
        try {
            return Long.parseLong(header);
        } catch (Exception e) {
            throw new ServiceException("获取 tenantId 失败");
        }
    }
}
