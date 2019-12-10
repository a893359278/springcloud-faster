package com.csp.github.auth.config;

import com.csp.github.auth.entity.AuthResultType;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.rabc.client.AdminUserClient;
import com.csp.github.rabc.entity.AdminUser;
import com.csp.github.rabc.entity.Permission;
import com.csp.github.rabc.entity.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author 陈少平
 * @date 2019-11-23 21:40
 */
public class RabcUserDetailsService implements UserDetailsService {

    @Resource
    AdminUserClient adminUserClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        AdminUser user = adminUserClient.getUserFullInfoByUsername(username);
        if (user == null) {
            throw new ServiceException(AuthResultType.USERNAME_PASSWORD_INCORRECT);
        }
        String permissionPrefix = "PERMISSION_";
        for (Permission permission : user.getPermissions()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permissionPrefix + permission.getName()));
        }
        String rolePrefix = "ROLE_";
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(rolePrefix + role.getCode()));
        }

        return new User(user.getUsername(),  user.getPassword(), grantedAuthorities);
    }
}
