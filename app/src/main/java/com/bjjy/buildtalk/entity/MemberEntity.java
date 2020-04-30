package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/18 2:11 PM
 * @project BuildTalk
 * @description:
 */
public class MemberEntity implements Serializable {

    /**
     * circleUser : [{"user_id":139,"headImage":"https://www.51jiantan.com/static/image/1.jpg","name":"建谈新用户  "},{"user_id":138,"headImage":"https://www.51jiantan.com/static/image/1.jpg","updateStauts":0,"is_author":0,"name":"LIU"},{"user_id":145,"headImage":"https://www.51jiantan.com/static/image/1.jpg","updateStauts":0,"is_author":0,"name":"建谈新用户"}]
     * page : 1
     * page_count : 1
     */

    private String page;
    private int page_count;
    private List<CircleUserBean> circleUser;

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

    public List<CircleUserBean> getCircleUser() {
        return circleUser;
    }

    public void setCircleUser(List<CircleUserBean> circleUser) {
        this.circleUser = circleUser;
    }

    public static class CircleUserBean {
        /**
         * user_id : 139
         * headImage : https://www.51jiantan.com/static/image/1.jpg
         * name : 建谈新用户
         * updateStauts : 0
         * is_author : 0
         */

        private int user_id;
        private String headImage;
        private String name;
        private int updateStauts;
        private int is_author;
        private String circle_master;
        private String liveness;
        private String countThemeParise;
        private String countThemeCommentNum;

        public String getCircle_master() {
            return circle_master;
        }

        public void setCircle_master(String circle_master) {
            this.circle_master = circle_master;
        }

        public String getLiveness() {
            return liveness;
        }

        public void setLiveness(String liveness) {
            this.liveness = liveness;
        }

        public String getCountThemeParise() {
            return countThemeParise;
        }

        public void setCountThemeParise(String countThemeParise) {
            this.countThemeParise = countThemeParise;
        }

        public String getCountThemeCommentNum() {
            return countThemeCommentNum;
        }

        public void setCountThemeCommentNum(String countThemeCommentNum) {
            this.countThemeCommentNum = countThemeCommentNum;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUpdateStauts() {
            return updateStauts;
        }

        public void setUpdateStauts(int updateStauts) {
            this.updateStauts = updateStauts;
        }

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }
    }
}
