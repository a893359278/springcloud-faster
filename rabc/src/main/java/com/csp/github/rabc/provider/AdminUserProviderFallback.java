package com.csp.github.rabc.provider;

import com.csp.github.rabc.entity.AdminUser;

/**
 * @author 陈少平
 * @date 2019-12-06 08:21
 */
public class AdminUserProviderFallback implements AdminUserProvider{

    @Override
    public AdminUser userFullInfo(Long id) {
        return null;
    }
}
