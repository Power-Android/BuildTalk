package com.bjjy.buildtalk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

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
    private String top_keyword;
    private List<ThemeInfoBean> themeInfo;

    public String getTop_keyword() {
        return top_keyword;
    }

    public void setTop_keyword(String top_keyword) {
        this.top_keyword = top_keyword;
    }

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

    public static class ThemeInfoBean implements Serializable, MultiItemEntity {
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
        private int is_retract = 1;

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
        private List<ThemePdfBean> theme_pdf;
        private List<ThemeVideoBean> theme_video;
        private List<CommentContentBean> comment_content;
        private List<PariseNickNameBean> parise_nickName;
        private ParentThemeInfoBean parent_themeInfo;
        private int page;
        private int page_count;
        private int countCommentNum;
        private int themeCountParise;
        private int is_top;
        private int countParise;
        private int parent_isDelete;
        private int is_find;
        private int reprint_themeId;
        private int parent_themeId;
        private String circle_name;
        private int is_attention;
        private int parent_userId;
        private int parent_name;
        private int countComment;

        public int getCountComment() {
            return countComment;
        }

        public void setCountComment(int countComment) {
            this.countComment = countComment;
        }

        public int getIs_find() {
            return is_find;
        }

        public void setIs_find(int is_find) {
            this.is_find = is_find;
        }

        public int getReprint_themeId() {
            return reprint_themeId;
        }

        public void setReprint_themeId(int reprint_themeId) {
            this.reprint_themeId = reprint_themeId;
        }

        public int getParent_themeId() {
            return parent_themeId;
        }

        public void setParent_themeId(int parent_themeId) {
            this.parent_themeId = parent_themeId;
        }

        public String getCircle_name() {
            return circle_name;
        }

        public void setCircle_name(String circle_name) {
            this.circle_name = circle_name;
        }

        public int getIs_attention() {
            return is_attention;
        }

        public void setIs_attention(int is_attention) {
            this.is_attention = is_attention;
        }

        public int getParent_userId() {
            return parent_userId;
        }

        public void setParent_userId(int parent_userId) {
            this.parent_userId = parent_userId;
        }

        public int getParent_name() {
            return parent_name;
        }

        public void setParent_name(int parent_name) {
            this.parent_name = parent_name;
        }

        public int getParent_isDelete() {
            return parent_isDelete;
        }

        public void setParent_isDelete(int parent_isDelete) {
            this.parent_isDelete = parent_isDelete;
        }

        public ParentThemeInfoBean getParent_themeInfo() {
            return parent_themeInfo;
        }

        public void setParent_themeInfo(ParentThemeInfoBean parent_themeInfo) {
            this.parent_themeInfo = parent_themeInfo;
        }

        public static class ParentThemeInfoBean implements Serializable {
            /**
             * theme_id : 1148
             * theme_content :
             * theme_image : []
             * theme_pdf : [{"pdf_id":38,"pdf_url":"https://ciip-dev.oss-cn-beijing.aliyuncs.com/JianTanApp_iOS/themePDFs/0_2311590513062.pdf","pdf_name":"港姐.pdf"}]
             * theme_video : []
             * publish_time : 2020-05-26 17:09:45
             * user_id : 231
             * circle_id : 0
             * is_choiceness : 0
             * source : ios
             * is_top : 0
             * top_time : 0
             * is_find : 1
             * reprint_themeId : 0
             * parent_themeId : 1148
             * countParise : 0
             * is_retract : 1
             * name : 李佳
             * headImage : https://www.51jiantan.com/static/image/157501144912826.png
             * is_circleMaster : 0
             * is_parise : 0
             * is_collect : 0
             * is_author : 1
             */

            private int theme_id;
            private String theme_content;
            private String publish_time;
            private int user_id;
            private int circle_id;
            private int is_choiceness;
            private String source;
            private int is_top;
            private int top_time;
            private int is_find;
            private int reprint_themeId;
            private int parent_themeId;
            private int countParise;
            private int is_retract;
            private String name;
            private String headImage;
            private int is_circleMaster;
            private int is_parise;
            private int is_collect;
            private int is_author;
            private List<ThemeImageBean> theme_image;
            private List<com.bjjy.buildtalk.entity.ThemePdfBean> theme_pdf;
            private List<ThemeVideoBean> theme_video;

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

            public int getIs_choiceness() {
                return is_choiceness;
            }

            public void setIs_choiceness(int is_choiceness) {
                this.is_choiceness = is_choiceness;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public int getIs_top() {
                return is_top;
            }

            public void setIs_top(int is_top) {
                this.is_top = is_top;
            }

            public int getTop_time() {
                return top_time;
            }

            public void setTop_time(int top_time) {
                this.top_time = top_time;
            }

            public int getIs_find() {
                return is_find;
            }

            public void setIs_find(int is_find) {
                this.is_find = is_find;
            }

            public int getReprint_themeId() {
                return reprint_themeId;
            }

            public void setReprint_themeId(int reprint_themeId) {
                this.reprint_themeId = reprint_themeId;
            }

            public int getParent_themeId() {
                return parent_themeId;
            }

            public void setParent_themeId(int parent_themeId) {
                this.parent_themeId = parent_themeId;
            }

            public int getCountParise() {
                return countParise;
            }

            public void setCountParise(int countParise) {
                this.countParise = countParise;
            }

            public int getIs_retract() {
                return is_retract;
            }

            public void setIs_retract(int is_retract) {
                this.is_retract = is_retract;
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

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public int getIs_author() {
                return is_author;
            }

            public void setIs_author(int is_author) {
                this.is_author = is_author;
            }

            public List<ThemeImageBean> getTheme_image() {
                return theme_image;
            }

            public void setTheme_image(List<ThemeImageBean> theme_image) {
                this.theme_image = theme_image;
            }

            public List<com.bjjy.buildtalk.entity.ThemePdfBean> getTheme_pdf() {
                return theme_pdf;
            }

            public void setTheme_pdf(List<com.bjjy.buildtalk.entity.ThemePdfBean> theme_pdf) {
                this.theme_pdf = theme_pdf;
            }

            public List<ThemeVideoBean> getTheme_video() {
                return theme_video;
            }

            public void setTheme_video(List<ThemeVideoBean> theme_video) {
                this.theme_video = theme_video;
            }

            public static class ThemePdfBean {
                /**
                 * pdf_id : 38
                 * pdf_url : https://ciip-dev.oss-cn-beijing.aliyuncs.com/JianTanApp_iOS/themePDFs/0_2311590513062.pdf
                 * pdf_name : 港姐.pdf
                 */

                private int pdf_id;
                private String pdf_url;
                private String pdf_name;

                public int getPdf_id() {
                    return pdf_id;
                }

                public void setPdf_id(int pdf_id) {
                    this.pdf_id = pdf_id;
                }

                public String getPdf_url() {
                    return pdf_url;
                }

                public void setPdf_url(String pdf_url) {
                    this.pdf_url = pdf_url;
                }

                public String getPdf_name() {
                    return pdf_name;
                }

                public void setPdf_name(String pdf_name) {
                    this.pdf_name = pdf_name;
                }
            }
        }

        public int getCountParise() {
            return countParise;
        }

        public void setCountParise(int countParise) {
            this.countParise = countParise;
        }

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

        @Override
        public int getItemType() {
            return is_retract;
        }

        public int getIs_retract() {
            return is_retract;
        }

        public void setIs_retract(int is_retract) {
            this.is_retract = is_retract;
        }
    }
}
