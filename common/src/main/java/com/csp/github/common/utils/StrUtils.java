package com.csp.github.common.utils;

/**
 * @author 陈少平
 * @date 2019-11-16 14:31
 */
public class StrUtils {

    private StrUtils(){}

    private static final String utf8 = "UTF-8";

    public static String bytes2Str(byte[] bytes) {
        try {
            return new String(bytes, utf8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
