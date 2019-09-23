package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/3 5:34 PM
 * @project BuildTalk
 * @description:
 */
public class ThemeInfoEntity implements Serializable {

    /**
     * themeInfo : [{"theme_id":11,"theme_content":"圈子[我是圈子名称]创建成功","theme_image":[{"pic_id":"45","pic_url":"https://www.51jiantan.com/static/image/activity.png"},{"pic_id":"40","pic_url":"https://www.51jiantan.com/static/image/activity.png"}],"publish_time":"2019-05-29 12:23:36","user_id":138,"circle_id":20,"source":"ios","commentPage":1,"commentPage_count":2,"comment_content":[{"comment_id":168,"user_id":138,"content":"1","name":"建谈新用户","is_author":0},{"comment_id":164,"user_id":138,"content":"2222222222","name":"建谈新用户","is_author":0},{"comment_id":163,"user_id":138,"content":"12edwke;lcmlwmcawm,cvlemvlemclem,co,wec,leclkecmlkemc;acamvlemcvaemcec,wodkx,opwx,wopc,lkmclmckc,paw","name":"建谈新用户","is_author":0},{"comment_id":162,"user_id":138,"content":"Dwdx2wd12","name":"建谈新用户","is_author":0},{"comment_id":161,"user_id":138,"content":"Dwdx2wd12","name":"建谈新用户","is_author":0},{"comment_id":160,"user_id":138,"content":"123e43435","name":"建谈新用户","is_author":0},{"comment_id":159,"user_id":138,"content":"1","name":"建谈新用户","is_author":0},{"comment_id":158,"user_id":138,"content":"1","name":"建谈新用户","is_author":0},{"comment_id":155,"user_id":138,"content":"1234567890","name":"建谈新用户","is_author":0},{"comment_id":154,"user_id":138,"content":"12345678","name":"建谈新用户","is_author":0}],"parise_nickName":[{"name":"建谈新用户","user_id":138,"user_type":1}],"name":"建谈新用户","headImage":"https://www.51jiantan.com/static/image/1.jpg","is_circleMaster":1,"is_parise":1}]
     * page : 1
     * page_count : 1
     */

    private String page;
    private int page_count;
    private String countTheme;
    private List<ThemeInfoBean> themeInfo;

    public String getCountTheme() {
        return countTheme;
    }

    public void setCountTheme(String countTheme) {
        this.countTheme = countTheme;
    }

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

    public List<ThemeInfoBean> getThemeInfo() {
        return themeInfo;
    }

    public void setThemeInfo(List<ThemeInfoBean> themeInfo) {
        this.themeInfo = themeInfo;
    }

    public static class ThemeInfoBean implements Serializable{
        /**
         * theme_id : 11
         * theme_content : 圈子[我是圈子名称]创建成功
         * theme_image : [{"pic_id":"45","pic_url":"https://www.51jiantan.com/static/image/activity.png"},{"pic_id":"40","pic_url":"https://www.51jiantan.com/static/image/activity.png"}]
         * publish_time : 2019-05-29 12:23:36
         * user_id : 138
         * circle_id : 20
         * source : ios
         * commentPage : 1
         * commentPage_count : 2
         * comment_content : [{"comment_id":168,"user_id":138,"content":"1","name":"建谈新用户","is_author":0},{"comment_id":164,"user_id":138,"content":"2222222222","name":"建谈新用户","is_author":0},{"comment_id":163,"user_id":138,"content":"12edwke;lcmlwmcawm,cvlemvlemclem,co,wec,leclkecmlkemc;acamvlemcvaemcec,wodkx,opwx,wopc,lkmclmckc,paw","name":"建谈新用户","is_author":0},{"comment_id":162,"user_id":138,"content":"Dwdx2wd12","name":"建谈新用户","is_author":0},{"comment_id":161,"user_id":138,"content":"Dwdx2wd12","name":"建谈新用户","is_author":0},{"comment_id":160,"user_id":138,"content":"123e43435","name":"建谈新用户","is_author":0},{"comment_id":159,"user_id":138,"content":"1","name":"建谈新用户","is_author":0},{"comment_id":158,"user_id":138,"content":"1","name":"建谈新用户","is_author":0},{"comment_id":155,"user_id":138,"content":"1234567890","name":"建谈新用户","is_author":0},{"comment_id":154,"user_id":138,"content":"12345678","name":"建谈新用户","is_author":0}]
         * parise_nickName : [{"name":"建谈新用户","user_id":138,"user_type":1}]
         * name : 建谈新用户
         * headImage : https://www.51jiantan.com/static/image/1.jpg
         * is_circleMaster : 1
         * is_parise : 1
         */

        private int theme_id;
        private String theme_content;
        private String publish_time;
        private String user_id;
        private int circle_id;
        private String source;
        private int commentPage;
        private int commentPage_count;
        private String name;
        private String headImage;
        private String is_circleMaster;
        private int is_parise;
        private int is_collect;
        private int is_choiceness;
        private String is_author;
        private List<ThemeImageBean> theme_image;
        private List<CommentContentBean> comment_content;
        private List<PariseNickNameBean> parise_nickName;
        private int page;
        private int page_count;
        private int countCommentNum;
        private int themeCountParise;
        private int is_top;

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public int getThemeCountParise() {
            return themeCountParise;
        }

        public void setThemeCountParise(int themeCountParise) {
            this.themeCountParise = themeCountParise;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getCountCommentNum() {
            return countCommentNum;
        }

        public void setCountCommentNum(int countCommentNum) {
            this.countCommentNum = countCommentNum;
        }

        public String getIs_author() {
            return is_author;
        }

        public void setIs_author(String is_author) {
            this.is_author = is_author;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getIs_choiceness() {
            return is_choiceness;
        }

        public void setIs_choiceness(int is_choiceness) {
            this.is_choiceness = is_choiceness;
        }

        public int getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(int theme_id) {
            this.theme_id = theme_id;
        }

        public String getTheme_content() {
            return theme_content;
        }

        public void setTheme_content(String theme_content) {
            this.theme_content = theme_content;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getCommentPage() {
            return commentPage;
        }

        public void setCommentPage(int commentPage) {
            this.commentPage = commentPage;
        }

        public int getCommentPage_count() {
            return commentPage_count;
        }

        public void setCommentPage_count(int commentPage_count) {
            this.commentPage_count = commentPage_count;
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

        public String getIs_circleMaster() {
            return is_circleMaster;
        }

        public void setIs_circleMaster(String is_circleMaster) {
            this.is_circleMaster = is_circleMaster;
        }

        public int getIs_parise() {
            return is_parise;
        }

        public void setIs_parise(int is_parise) {
            this.is_parise = is_parise;
        }

        public List<ThemeImageBean> getTheme_image() {
            return theme_image;
        }

        public void setTheme_image(List<ThemeImageBean> theme_image) {
            this.theme_image = theme_image;
        }

        public List<CommentContentBean> getComment_content() {
            return comment_content;
        }

        public void setComment_content(List<CommentContentBean> comment_content) {
            this.comment_content = comment_content;
        }

        public List<PariseNickNameBean> getParise_nickName() {
            return parise_nickName;
        }

        public void setParise_nickName(List<PariseNickNameBean> parise_nickName) {
            this.parise_nickName = parise_nickName;
        }
    }
}
