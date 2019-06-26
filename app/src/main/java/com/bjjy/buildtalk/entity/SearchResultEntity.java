package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/3 1:59 PM
 * @project BuildTalk
 * @description:
 */
public class SearchResultEntity implements Serializable {

    /**
     * circleInfo : [{"circle_id":22,"user_id":138,"circle_image":{"pic_id":56,"pic_url":"https://jt.chinabim.com/static/image/155910952286724.png","savetime":1559109525},"circle_name":"我是圈子123","circle_tags":["BIM","装配式","钢结构"],"type":"话题","data_id":null,"name":"建谈新用户","is_author":0,"type_id":1},{"circle_id":21,"user_id":4,"circle_image":{"pic_id":55,"pic_url":"https://jt.chinabim.com/static/image/155910844551201.png","savetime":1559108445},"circle_name":"Lpn的圈子","circle_tags":["BIM","装配式","lpn"],"type":"话题","data_id":null,"name":"♞木有年輪","is_author":0,"type_id":1},{"circle_id":20,"user_id":138,"circle_image":{"pic_id":50,"pic_url":"https://jt.chinabim.com/static/image/155910381678235.png","savetime":1559103816},"circle_name":"我是圈子名称","circle_tags":["BIM",""],"type":"话题","data_id":null,"name":"建谈新用户","is_author":0,"type_id":1},{"circle_id":2,"user_id":12,"circle_image":{"pic_id":37,"pic_url":"https://www.51jiantan.com/static/image/activity.png","savetime":1553324666},"circle_name":"天的测试圈子","circle_tags":["BIM","钢结构","装配式","一体化质量体系","追风堂"],"type":"话题","data_id":null,"name":"小陀螺","is_author":0,"type_id":1},{"circle_id":1,"user_id":4,"circle_image":{"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null},"circle_name":"朋友圈","circle_tags":["钢结构","装配式","BIM","建筑"],"type":"话题","data_id":null,"name":"♞木有年輪","is_author":0,"type_id":1}]
     * page : 1
     * page_count : 1
     */

    private String page;
    private int page_count;
    private List<CircleInfoBean> circleInfo;
    private List<AuthorInfoBean> authorInfo;


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

    public List<AuthorInfoBean> getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(List<AuthorInfoBean> authorInfo) {
        this.authorInfo = authorInfo;
    }

    public static class CircleInfoBean {
        /**
         * circle_id : 22
         * user_id : 138
         * circle_image : {"pic_id":56,"pic_url":"https://jt.chinabim.com/static/image/155910952286724.png","savetime":1559109525}
         * circle_name : 我是圈子123
         * circle_tags : ["BIM","装配式","钢结构"]
         * type : 话题
         * data_id : null
         * name : 建谈新用户
         * is_author : 0
         * type_id : 1
         */

        private int circle_id;
        private int user_id;
        private CircleImageBean circle_image;
        private String circle_name;
        private String type;
        private Object data_id;
        private String name;
        private int is_author;
        private int type_id;
        private List<String> circle_tags;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getData_id() {
            return data_id;
        }

        public void setData_id(Object data_id) {
            this.data_id = data_id;
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

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public List<String> getCircle_tags() {
            return circle_tags;
        }

        public void setCircle_tags(List<String> circle_tags) {
            this.circle_tags = circle_tags;
        }

        public static class CircleImageBean {
            /**
             * pic_id : 56
             * pic_url : https://jt.chinabim.com/static/image/155910952286724.png
             * savetime : 1559109525
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

    public static class AuthorInfoBean {
        /**
         * author_id : 10
         * author_name : 张鸣
         * author_pic : https://www.51jiantan.com/static/image/zhangming.png
         * author_desc : 民盟成员、高级工程师
         * author_intro : 悉尼大学工商管理硕士
         * sign : ["平台","数据","BIM"]
         * education : 博士
         */

        private int author_id;
        private String author_name;
        private String author_pic;
        private String author_desc;
        private String author_intro;
        private String education;
        private List<String> sign;

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_pic() {
            return author_pic;
        }

        public void setAuthor_pic(String author_pic) {
            this.author_pic = author_pic;
        }

        public String getAuthor_desc() {
            return author_desc;
        }

        public void setAuthor_desc(String author_desc) {
            this.author_desc = author_desc;
        }

        public String getAuthor_intro() {
            return author_intro;
        }

        public void setAuthor_intro(String author_intro) {
            this.author_intro = author_intro;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public List<String> getSign() {
            return sign;
        }

        public void setSign(List<String> sign) {
            this.sign = sign;
        }
    }
}
