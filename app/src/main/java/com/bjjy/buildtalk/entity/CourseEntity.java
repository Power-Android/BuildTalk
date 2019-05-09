package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/5/6 9:43 AM
 * @project BuildTalk
 * @description:
 */
public class CourseEntity implements Serializable {



    /**
     * circleInfo : [{"circle_id":1,"user_id":4,"circle_image":{"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null},"circle_name":"朋友圈","name":"♞木有年輪","is_author":0},{"circle_id":2,"user_id":12,"circle_image":{"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null},"circle_name":"天的测试圈子","name":"小陀螺","is_author":0},{"circle_id":4,"user_id":8,"circle_image":{"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null},"circle_name":"黑猫警长","name":"一","is_author":0}]
     * page_count : 4
     * page : 1
     */

    private int page_count;
    private String page;
    private List<CircleInfoBean> circleInfo;

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<CircleInfoBean> getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(List<CircleInfoBean> circleInfo) {
        this.circleInfo = circleInfo;
    }

    public static class CircleInfoBean {
        /**
         * circle_id : 1
         * user_id : 4
         * circle_image : {"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null}
         * circle_name : 朋友圈
         * name : ♞木有年輪
         * is_author : 0
         */

        private int circle_id;
        private int user_id;
        private CircleImageBean circle_image;
        private String circle_name;
        private String name;
        private int is_author;
        private String data_id;
        private String author_desc;
        private List<String> circle_tags;
        private String countCourse;
        private String course_money;
        private String countUser;

        public String getCourse_money() {
            return course_money;
        }

        public void setCourse_money(String course_money) {
            this.course_money = course_money;
        }

        public String getCountUser() {
            return countUser;
        }

        public void setCountUser(String countUser) {
            this.countUser = countUser;
        }

        public String getCountCourse() {
            return countCourse;
        }

        public void setCountCourse(String countCourse) {
            this.countCourse = countCourse;
        }

        public String getData_id() {
            return data_id;
        }

        public void setData_id(String data_id) {
            this.data_id = data_id;
        }

        public String getAuthor_desc() {
            return author_desc;
        }

        public void setAuthor_desc(String author_desc) {
            this.author_desc = author_desc;
        }

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

        public List<String> getCircle_tags() {
            return circle_tags;
        }

        public void setCircle_tags(List<String> circle_tags) {
            this.circle_tags = circle_tags;
        }

        public static class CircleImageBean {
            /**
             * pic_id : 20
             * pic_url : https://www.51jiantan.com/static/image/2.jpg
             * savetime : null
             */

            private int pic_id;
            private String pic_url;
            private Object savetime;

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

            public Object getSavetime() {
                return savetime;
            }

            public void setSavetime(Object savetime) {
                this.savetime = savetime;
            }
        }
    }
}
