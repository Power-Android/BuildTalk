package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/18 11:23 AM
 * @project BuildTalk
 * @description:
 */
public class MyCardEntity implements Serializable {

    /**
     * circle_id : 26
     * user_id : 139
     * circle_image : {"pic_id":60,"pic_url":"https://jt.chinabim.com/static/image/155953163633789.jpg","savetime":1559531636}
     * circle_name : 测试11111
     * name : 建谈新用户
     * master_pic : https://www.51jiantan.com/static/image/1.jpg
     * is_author : 0
     * is_circleMaster : 0
     * headImage : https://www.51jiantan.com/static/image/1.jpg
     * join_day : 1
     * countTheme : 1
     * countpraise : 2
     * circle_code : https://jt.chinabim.com/static/image/26145.png
     */

    private String circle_id;
    private String user_id;
    private CircleImageBean circle_image;
    private String circle_name;
    private String name;
    private String master_pic;
    private String is_author;
    private String is_circleMaster;
    private String headImage;
    private String join_day;
    private String countTheme;
    private String countpraise;
    private String circle_code;
    private String create_day;

    public String getCreate_day() {
        return create_day;
    }

    public void setCreate_day(String create_day) {
        this.create_day = create_day;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaster_pic() {
        return master_pic;
    }

    public void setMaster_pic(String master_pic) {
        this.master_pic = master_pic;
    }

    public String getIs_author() {
        return is_author;
    }

    public void setIs_author(String is_author) {
        this.is_author = is_author;
    }

    public String getIs_circleMaster() {
        return is_circleMaster;
    }

    public void setIs_circleMaster(String is_circleMaster) {
        this.is_circleMaster = is_circleMaster;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getJoin_day() {
        return join_day;
    }

    public void setJoin_day(String join_day) {
        this.join_day = join_day;
    }

    public String getCountTheme() {
        return countTheme;
    }

    public void setCountTheme(String countTheme) {
        this.countTheme = countTheme;
    }

    public String getCountpraise() {
        return countpraise;
    }

    public void setCountpraise(String countpraise) {
        this.countpraise = countpraise;
    }

    public String getCircle_code() {
        return circle_code;
    }

    public void setCircle_code(String circle_code) {
        this.circle_code = circle_code;
    }

    public static class CircleImageBean {
        /**
         * pic_id : 60
         * pic_url : https://jt.chinabim.com/static/image/155953163633789.jpg
         * savetime : 1559531636
         */

        private int pic_id;
        private String pic_url;
        private String savetime;

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

        public String getSavetime() {
            return savetime;
        }

        public void setSavetime(String savetime) {
            this.savetime = savetime;
        }
    }
}
