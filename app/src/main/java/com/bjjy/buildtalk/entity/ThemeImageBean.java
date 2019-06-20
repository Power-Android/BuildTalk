package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/14 3:07 PM
 * @project BuildTalk
 * @description:
 */
public class ThemeImageBean implements Serializable {
    /**
     * pic_id : 45
     * pic_url : https://www.51jiantan.com/static/image/activity.png
     */

    private String pic_id;
    private String pic_url;

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
