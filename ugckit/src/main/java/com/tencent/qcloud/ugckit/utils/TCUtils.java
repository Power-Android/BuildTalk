package com.tencent.qcloud.ugckit.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TCUtils {

    public static String md5(@NonNull String string) {

        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }

        return hex.toString();
    }

    // 字符串截断
    @Nullable
    public static String getLimitString(@Nullable String source, int length) {
        if (null != source && source.length() > length) {
            return source.substring(0, length) + "...";
        }
        return source;
    }

}
