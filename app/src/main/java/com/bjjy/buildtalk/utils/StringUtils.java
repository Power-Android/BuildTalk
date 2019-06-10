package com.bjjy.buildtalk.utils;

import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/31 4:11 PM
 * @project BuildTalk
 * @description:
 */
public class StringUtils {

    public static String listToString(List<CircleTagEntity> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getTag_name()).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String listToString1(List<ThemeInfoEntity.ThemeInfoBean.PariseNickNameBean> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getName()).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }
}
