package com.bjjy.buildtalk.utils;

import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.PariseNickNameBean;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

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

    public static String listToString1(List<PariseNickNameBean> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getName()).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String listToString2(List<ThemeImageBean> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getPic_url()).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String listToString3(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append("\n");
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }
}
