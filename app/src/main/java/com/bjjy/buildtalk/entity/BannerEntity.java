package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/5 4:05 PM
 * @project BuildTalk
 * @description:
 */
public class BannerEntity implements Serializable {

    /**
     * pic_id : 39
     * data_id : 8
     * type_id : 1
     * pic_url : https://www.zhuti1.com
     * type_name : course
     */

    private int pic_id;
    private int data_id;
    private int type_id;
    private String pic_url;
    private String type_name;

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
