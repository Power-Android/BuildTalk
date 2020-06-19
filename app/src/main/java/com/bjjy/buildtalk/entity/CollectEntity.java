package com.bjjy.buildtalk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/24 10:08 AM
 * @project BuildTalk
 * @description:
 */
public class CollectEntity implements Serializable {

    /**
     * page : 1
     * page_count : 1
     * myCollectInfo : [{"collect_id":70,"user_id":10,"theme_id":2,"name":"♞木有年輪","headImage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132","theme_content":"发布主题测试277","theme_image":[{"pic_id":"46","pic_url":"https://www.zhuti1.com"},{"pic_id":"47","pic_url":"https://www.zhuti2.com"}],"circle_id":3,"circle_name":"Revit课程","theme_status":1},{"collect_id":71,"user_id":10,"theme_id":3,"name":"张鸣","headImage":"https://www.51jiantan.com/static/image/zhangming.png","theme_content":"fdsafds","theme_image":[{"pic_id":"39","pic_url":"https://www.51jiantan.com/static/image/6.jpg"},{"pic_id":"40","pic_url":"https://www.51jiantan.com/static/image/activity.png"}],"circle_id":2,"circle_name":"ArchiCAD课程","theme_status":2}]
     */

    private String page;
    private int page_count;
    private List<MyCollectInfoBean> myCollectInfo;

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

    public List<MyCollectInfoBean> getMyCollectInfo() {
        return myCollectInfo;
    }

    public void setMyCollectInfo(List<MyCollectInfoBean> myCollectInfo) {
        this.myCollectInfo = myCollectInfo;
    }


    public static class MyCollectInfoBean implements Serializable, MultiItemEntity{
        /**
         * collect_id : 70
         * user_id : 10
         * theme_id : 2
         * name : ♞木有年輪
         * headImage : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132
         * theme_content : 发布主题测试277
         * theme_image : [{"pic_id":"46","pic_url":"https://www.zhuti1.com"},{"pic_id":"47","pic_url":"https://www.zhuti2.com"}]
         * circle_id : 3
         * circle_name : Revit课程
         * theme_status : 1
         */

        private int collect_id;
        private int user_id;
        private int theme_id;
        private String name;
        private String headImage;
        private String theme_content;
        private int circle_id;
        private String circle_name;
        private int theme_status;
        private List<ThemeImageBean> theme_image;
        private List<ThemePdfBean> theme_pdf;
        private List<ThemeVideoBean> theme_video;
        private int itemType;

        public MyCollectInfoBean(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public int getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(int collect_id) {
            this.collect_id = collect_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(int theme_id) {
            this.theme_id = theme_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getTheme_content() {
            return theme_content;
        }

        public void setTheme_content(String theme_content) {
            this.theme_content = theme_content;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public String getCircle_name() {
            return circle_name;
        }

        public void setCircle_name(String circle_name) {
            this.circle_name = circle_name;
        }

        public int getTheme_status() {
            return theme_status;
        }

        public void setTheme_status(int theme_status) {
            this.theme_status = theme_status;
        }

        public List<ThemeImageBean> getTheme_image() {
            return theme_image;
        }

        public void setTheme_image(List<ThemeImageBean> theme_image) {
            this.theme_image = theme_image;
        }

        public List<ThemePdfBean> getTheme_pdf() {
            return theme_pdf;
        }

        public void setTheme_pdf(List<ThemePdfBean> theme_pdf) {
            this.theme_pdf = theme_pdf;
        }

        public List<ThemeVideoBean> getTheme_video() {
            return theme_video;
        }

        public void setTheme_video(List<ThemeVideoBean> theme_video) {
            this.theme_video = theme_video;
        }

        public static class ThemeImageBean {
            /**
             * pic_id : 46
             * pic_url : https://www.zhuti1.com
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
    }
}
