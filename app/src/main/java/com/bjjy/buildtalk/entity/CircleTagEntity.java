package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/21 3:43 PM
 * @project BuildTalk
 * @description:
 */
public class CircleTagEntity implements Serializable {

    private String content;
    private boolean isCustom = false;
    private boolean isSelected = false;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public CircleTagEntity(String content, boolean isCustom) {
        this.content = content;
        this.isCustom = isCustom;
    }

    public CircleTagEntity(String content, boolean isCustom, boolean isSelected) {
        this.content = content;
        this.isCustom = isCustom;
        this.isSelected = isSelected;
    }
}
