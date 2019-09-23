package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019-09-12 09:43
 * @project BuildTalk
 * @description:
 */
public class DissertationEntity implements Serializable {

    /**
     * dissertation_id : 2
     * dissertation_title : 2019年中国数字产业峰会
     * dissertation_pic : 21
     * pic_url : https://jt.chinabim.com/static/image//banner2.png
     */

    private int dissertation_id;
    private String dissertation_title;
    private String dissertation_pic;
    private String pic_url;

    public int getDissertation_id() {
        return dissertation_id;
    }

    public void setDissertation_id(int dissertation_id) {
        this.dissertation_id = dissertation_id;
    }

    public String getDissertation_title() {
        return dissertation_title;
    }

    public void setDissertation_title(String dissertation_title) {
        this.dissertation_title = dissertation_title;
    }

    public String getDissertation_pic() {
        return dissertation_pic;
    }

    public void setDissertation_pic(String dissertation_pic) {
        this.dissertation_pic = dissertation_pic;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
