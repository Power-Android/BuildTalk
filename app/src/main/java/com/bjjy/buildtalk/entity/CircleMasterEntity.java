package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/20 11:38 AM
 * @project BuildTalk
 * @description:
 */
public class CircleMasterEntity implements Serializable {

    /**
     * user_id : 4
     * is_author : 0
     * name : ♞木有年輪
     * headImage : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132
     * countAttention : 4
     * is_attention : 0
     */

    private int user_id;
    private int is_author;
    private String name;
    private String headImage;
    private int countAttention;
    private int is_attention;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_author() {
        return is_author;
    }

    public void setIs_author(int is_author) {
        this.is_author = is_author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getCountAttention() {
        return countAttention;
    }

    public void setCountAttention(int countAttention) {
        this.countAttention = countAttention;
    }

    public int getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }
}
