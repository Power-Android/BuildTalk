package com.bjjy.buildtalk.entity;

import com.bjjy.buildtalk.adapter.CircleAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/5/15 11:28 AM
 * @project BuildTalk
 * @description:
 */
public class CircleEntity implements Serializable {
    /**
     * page : 1
     * page_count : 1
     * circleInfo : [{"circle_id":26,"user_id":139,"circle_image":{"pic_id":60,"pic_url":"https://jt.chinabim.com/static/image/155953163633789.jpg","savetime":1559531636},"circle_name":"测试11111","type":1,"name":"建谈新用户","is_author":0}]
     */

    private String page;
    private int page_count;
    private List<CircleInfoBean> circleInfo;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public List<CircleInfoBean> getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(List<CircleInfoBean> circleInfo) {
        this.circleInfo = circleInfo;
    }


    public static class CircleInfoBean implements Serializable, MultiItemEntity{

        private int itemType = CircleAdapter.BODY_CONTENT;

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public CircleInfoBean(int itemType) {
            this.itemType = itemType;
        }

        /**
         * circle_id : 26
         * user_id : 139
         * circle_image : {"pic_id":60,"pic_url":"https://jt.chinabim.com/static/image/155953163633789.jpg","savetime":1559531636}
         * circle_name : 测试11111
         * type : 1
         * name : 建谈新用户
         * is_author : 0
         */

        private int circle_id;
        private int user_id;
        private CircleImageBean circle_image;
        private String circle_name;
        private int type;
        private String name;
        private int is_author;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }

        public static class CircleImageBean {
            /**
             * pic_id : 60
             * pic_url : https://jt.chinabim.com/static/image/155953163633789.jpg
             * savetime : 1559531636
             */

            private String pic_id;
            private String pic_url;
            private String savetime;

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

            public String getSavetime() {
                return savetime;
            }

            public void setSavetime(String savetime) {
                this.savetime = savetime;
            }
        }
    }
}
