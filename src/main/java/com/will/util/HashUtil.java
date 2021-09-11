package com.will.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 明白一个对象的hashcode是怎么算出来的  大家都知道是一个16进制地址 关键是这个16进制地址是怎么来的？
 */
public class HashUtil {

    public static void countHash(Object object) throws NoSuchFieldException, IllegalAccessException {
        // 手动计算HashCode
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        long hashCode = 0;
        for (long index = 7; index > 0; index--) {
            hashCode |= (unsafe.getByte(object, index) & 0xFF) << ((index-1)*8);
    }
        String code = Long.toHexString(hashCode);
        System.out.println("util‐‐‐‐‐‐‐‐‐‐‐0x"+code);

    }
}
