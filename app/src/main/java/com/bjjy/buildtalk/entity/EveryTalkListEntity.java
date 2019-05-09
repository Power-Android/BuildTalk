package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/5/6 2:51 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkListEntity implements Serializable {

    /**
     * newsInfo : [{"article_id":498,"article_title":"第13期 国家级装配式建筑研究机构落户津西钢铁","course_id":0,"publish_time":"2019-01-08 18:09:58","browsecount":3,"media_type":4}]
     * authorInfo : {"author_id":25,"author_name":"谭小建","author_pic":"https://www.51jiantan.com/static/image/tanxiaojian.png","author_desc":[""],"author_intro":"","remark":"<span style=\"font-size:14px;\"><strong>课程简介：<\/strong><\/span><br /><br /><span style=\"font-size:12px;\"> 每天小建与你分享建筑产业知识讯息<\/span><br /><br /><strong><span style=\"font-size:14px;\">课程亮点：<\/span><\/strong><br /><br /><span style=\"font-size:12px;\">《谭小建》只在建谈平台播出，且完全免费。小建不是一个讲者，而是和每一个&ldquo;建谈&rdquo;用户一样的学习者。<br /><br />每天产生的知识讯息那么多，你根本看不过来，小建会把自己学习、归纳、整理的建筑产业相关的知识讯息筛选后分享给你。<br /><br />《谭小建》每天下午18：10更新，全年无休，用极致浓缩的知识音频，向你传递正在出现的新知识讯息。<\/span>","received":null,"education":null,"countupdate":13}
     * page : 1
     * page_count : 13
     */

    private AuthorInfoBean authorInfo;
    private String page;
    private int page_count;
    private List<NewsInfoBean> newsInfo;

    public AuthorInfoBean getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(AuthorInfoBean authorInfo) {
        this.authorInfo = authorInfo;
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

    public List<NewsInfoBean> getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(List<NewsInfoBean> newsInfo) {
        this.newsInfo = newsInfo;
    }

    public static class AuthorInfoBean {
        /**
         * author_id : 25
         * author_name : 谭小建
         * author_pic : https://www.51jiantan.com/static/image/tanxiaojian.png
         * author_desc : [""]
         * author_intro :
         * remark : <span style="font-size:14px;"><strong>课程简介：</strong></span><br /><br /><span style="font-size:12px;"> 每天小建与你分享建筑产业知识讯息</span><br /><br /><strong><span style="font-size:14px;">课程亮点：</span></strong><br /><br /><span style="font-size:12px;">《谭小建》只在建谈平台播出，且完全免费。小建不是一个讲者，而是和每一个&ldquo;建谈&rdquo;用户一样的学习者。<br /><br />每天产生的知识讯息那么多，你根本看不过来，小建会把自己学习、归纳、整理的建筑产业相关的知识讯息筛选后分享给你。<br /><br />《谭小建》每天下午18：10更新，全年无休，用极致浓缩的知识音频，向你传递正在出现的新知识讯息。</span>
         * received : null
         * education : null
         * countupdate : 13
         */

        private int author_id;
        private String author_name;
        private String author_pic;
        private String author_intro;
        private String remark;
        private String received;
        private String education;
        private int countupdate;
        private List<String> author_desc;

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

        public String getAuthor_intro() {
            return author_intro;
        }

        public void setAuthor_intro(String author_intro) {
            this.author_intro = author_intro;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReceived() {
            return received;
        }

        public void setReceived(String received) {
            this.received = received;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public int getCountupdate() {
            return countupdate;
        }

        public void setCountupdate(int countupdate) {
            this.countupdate = countupdate;
        }

        public List<String> getAuthor_desc() {
            return author_desc;
        }

        public void setAuthor_desc(List<String> author_desc) {
            this.author_desc = author_desc;
        }
    }

    public static class NewsInfoBean {
        /**
         * article_id : 498
         * article_title : 第13期 国家级装配式建筑研究机构落户津西钢铁
         * course_id : 0
         * publish_time : 2019-01-08 18:09:58
         * browsecount : 3
         * media_type : 4
         */

        private int article_id;
        private String article_title;
        private int course_id;
        private String publish_time;
        private int browsecount;
        private int media_type;

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public int getBrowsecount() {
            return browsecount;
        }

        public void setBrowsecount(int browsecount) {
            this.browsecount = browsecount;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }
    }
}
