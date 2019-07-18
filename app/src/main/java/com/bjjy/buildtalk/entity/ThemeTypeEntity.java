package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/7/16 10:21 AM
 * @project BuildTalk
 * @description:
 */
public class ThemeTypeEntity implements Serializable{
    private String name;
    private int img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public ThemeTypeEntity(String name, int img) {
        this.name = name;
        this.img = img;
    }
}
