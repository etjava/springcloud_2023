package com.et.util;

import java.util.Locale;
import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().toUpperCase(Locale.ROOT).replaceAll("-","");
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
