package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/21 3:43 PM
 * @project BuildTalk
 * @description:
 */
public class CircleTagEntity implements Serializable {

    private String tag_name;
    private boolean isCustom = false;
    private boolean isSelected = false;

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public CircleTagEntity(String tag_name, boolean isCustom) {
        this.tag_name = tag_name;
        this.isCustom = isCustom;
    }

    public CircleTagEntity(String tag_name, boolean isCustom, boolean isSelected) {
        this.tag_name = tag_name;
        this.isCustom = isCustom;
        this.isSelected = isSelected;
    }
}
