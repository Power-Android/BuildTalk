package com.bjjy.buildtalk.utils;

import android.text.TextUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author power
 * @date 2019/4/29 10:36 AM
 * @project BuildTalk
 * @description: http请求头签名工具类
 */
public class HeaderUtils {
    /**
     * @param oriMap
     * @param isRise true为升序
     * @return 排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> oriMap, final boolean isRise) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<>((o1, o2) -> {
            if (isRise) {
                // 升序排序
                return o1.compareTo(o2);
            } else {
                // 降序排序
                return o2.compareTo(o1);
            }
        });
        for (Map.Entry<String, String> entry : oriMap.entrySet()) {
            if (TextUtils.isEmpty(entry.getKey()) || TextUtils.isEmpty(entry.getValue()))
                continue;
            sortMap.put(entry.getKey(), entry.getValue());
        }
        return sortMap;
    }


    /**
     * @param map
     * @return 签名(一般用)
     */
    public static String getSign(Map<String, String> map) {
        String secret = "a0ny1099ec8yek4wa1pi3l4cf2h86wptorv6o3einn79u";
        String sign = "";

        if (map != null) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry entry : entries) {
                sign += entry.getKey();
                sign += entry.getValue();
            }
        }

        sign = secret + sign + secret;

        sign = EncryptUtils.encryptMD5ToString(sign);

        sign = sign.toUpperCase();

        return sign;
    }

}
