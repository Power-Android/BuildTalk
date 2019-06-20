package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/18 6:17 PM
 * @project BuildTalk
 * @description:
 */
public class SearchCircleInfoEntity implements Serializable {

    /**
     * circle_id : 26
     * user_id : 139
     * circle_image : {"pic_id":60,"pic_url":"https://jt.chinabim.com/static/image/155953163633789.jpg","savetime":1559531636}
     * circle_name : 测试11111
     * circle_tags : com.bjjy.buildtalk.entity.CircleTagEntity@90a496c,com.bjjy.buildtalk.entity.CircleTagEntity@1485b35,com.bjjy.buildtalk.entity.CircleTagEntity@7972bca
     * circle_desc : 圈子介绍
     * type : 1
     * is_circleMaster : 0
     */

    private int circle_id;
    private int user_id;
    private CircleImageBean circle_image;
    private String circle_name;
    private String circle_tags;
    private String circle_desc;
    private int type;
    private int is_circleMaster;

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public CircleImageBean getCircle_image() {
        return circle_image;
    }

    public void setCircle_image(CircleImageBean circle_image) {
        this.circle_image = circle_image;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }

    public String getCircle_tags() {
        return circle_tags;
    }

    public void setCircle_tags(String circle_tags) {
        this.circle_tags = circle_tags;
    }

    public String getCircle_desc() {
        return circle_desc;
    }

    public void setCircle_desc(String circle_desc) {
        this.circle_desc = circle_desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_circleMaster() {
        return is_circleMaster;
    }

    public void setIs_circleMaster(int is_circleMaster) {
        this.is_circleMaster = is_circleMaster;
    }

    public static class CircleImageBean {
        /**
         * pic_id : 60
         * pic_url : https://jt.chinabim.com/static/image/155953163633789.jpg
         * savetime : 1559531636
         */

        private int pic_id;
        private String pic_url;
        private int savetime;

        public int getPic_id() {
            return pic_id;
        }

        public void setPic_id(int pic_id) {
            this.pic_id = pic_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getSavetime() {
            return savetime;
        }

        public void setSavetime(int savetime) {
            this.savetime = savetime;
        }
    }
}
