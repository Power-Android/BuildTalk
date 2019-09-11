package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019-09-10 15:42
 * @project BuildTalk
 * @description:
 */
public class ActivityEntity implements Serializable {

    /**
     * activity_id : 1
     * pic_id : 21
     * activity_url :
     * publish_time : 2019-09-10 11:05:43
     * is_show : 1
     * pic_url : https://jt.chinabim.com/static/image//banner2.png
     */

    private int activity_id;
    private int pic_id;
    private String activity_url;
    private String activity_title;
    private String publish_time;
    private int is_show;
    private String pic_url;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }

    public String getActivity_url() {
        return activity_url;
    }

    public void setActivity_url(String activity_url) {
        this.activity_url = activity_url;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
