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
    private List<ThemeInfoBean> themeInfo;

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

    public static class ThemeInfoBean {
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
        private int user_id;
        private int circle_id;
        private String source;
        private int commentPage;
        private int commentPage_count;
        private String name;
        private String headImage;
        private int is_circleMaster;
        private int is_parise;
        private List<ThemeImageBean> theme_image;
        private List<CommentContentBean> comment_content;
        private List<PariseNickNameBean> parise_nickName;

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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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

        public int getIs_circleMaster() {
            return is_circleMaster;
        }

        public void setIs_circleMaster(int is_circleMaster) {
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

        public static class ThemeImageBean {
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

        public static class CommentContentBean {
            /**
             * comment_id : 168
             * user_id : 138
             * content : 1
             * name : 建谈新用户
             * is_author : 0
             */

            private int comment_id;
            private int user_id;
            private String content;
            private String name;
            private int is_author;

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
        }

        public static class PariseNickNameBean {
            /**
             * name : 建谈新用户
             * user_id : 138
             * user_type : 1
             */

            private String name;
            private int user_id;
            private int user_type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }
        }
    }
}
