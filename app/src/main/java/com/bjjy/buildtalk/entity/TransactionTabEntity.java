package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/29 10:27 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionTabEntity implements Serializable {

    private String content;
    private boolean isSelected;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public TransactionTabEntity(String content, boolean isSelected) {
        this.content = content;
        this.isSelected = isSelected;
    }
}
