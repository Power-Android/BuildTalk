package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/5 5:29 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkEntity implements Serializable {

    /**
     * article_id : 498
     * article_title : 第13期 国家级装配式建筑研究机构落户津西钢铁
     */

    private int article_id;
    private String article_title;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }
}
