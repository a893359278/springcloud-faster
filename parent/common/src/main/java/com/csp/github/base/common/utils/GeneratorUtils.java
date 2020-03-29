package com.csp.github.base.common.utils;

import java.util.Random;

/**
 * @author 陈少平
 * @date 2019-12-08 10:13
 */
public class GeneratorUtils {
    private static final String s = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    public static String generator(int num) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            sb.append(s.charAt(random.nextInt(s.length())));
        }
        return sb.toString();
    }

    public static String generator() {
        return generator(8);
    }
}
