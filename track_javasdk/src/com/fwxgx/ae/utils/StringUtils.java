package com.fwxgx.ae.utils;

/**
 *
 */
public class StringUtils {
    public static boolean isNotEmpty(String str) {
        if (str != null && "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }
}
