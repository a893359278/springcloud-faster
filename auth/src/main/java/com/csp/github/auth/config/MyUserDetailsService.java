package com.csp.github.auth.config;

import com.csp.github.auth.entity.UserDo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        List<UserDo> list = jdbcTemplate.query("select * from `user` where `name` = ?", new Object[]{username}, new BeanPropertyRowMapper<>(UserDo.class));
        UserDo userDo = list.get(0);
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SB"));
        return new User(userDo.getName(),  userDo.getPassword(), grantedAuthorities);
    }
}
