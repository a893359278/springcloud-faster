package com.csp.github.base.common.utils;

import com.csp.github.base.common.security.BCryptPasswordEncoder;

/**
 * @author 陈少平
 * @date 2020-01-16 23:22
 */
public class BCryptUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(CharSequence charSequence) {
        return encoder.encode(charSequence);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
