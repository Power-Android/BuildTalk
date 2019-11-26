package com.bjjy.buildtalk.entity;

/**
 * @author power
 * @date 2019-11-19 14:22
 * @project BuildTalk
 * @description:
 */
public class ReasonEntity {

    private String content;
    private boolean isChecked = false;

    public ReasonEntity(String content) {
        this.content = content;
    }

    public ReasonEntity(String content, boolean isChecked) {
        this.content = content;
        this.isChecked = isChecked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
