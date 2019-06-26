package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/23 10:09 AM
 * @project BuildTalk
 * @description:
 */
public class MasterDetailEntity implements Serializable {

    /**
     * name : 赵伟宏
     * headImage : https://www.51jiantan.com/static/image/zhaoweihong.jpg
     * author_desc : 资源管理专家
     * author_intro : 地域经济互联网先行者
     * received : 世界和平就靠你了
     * education : null
     * bg_pic : https://www.51jiantan.com/static/image/zhaoweihong.jpg
     * articleInfo : [{"article_id":93,"article_title":"建筑工业互联网峰会-赵伟宏","article_desc":"建筑工业互联网峰会-赵伟宏","article_pic":"https://www.51jiantan.com/static/image/微信图片_20181106144207.png"}]
     * countMyCircle : 0
     * remark :
     * is_author : 1
     * countMyFans : 0
     * countMyCollect : 0
     * countMyAttention : 0
     * is_attention : 0
     */

    private String name;
    private String headImage;
    private String author_desc;
    private String author_intro;
    private String received;
    private String education;
    private String bg_pic;
    private int countMyCircle;
    private String remark;
    private int is_author;
    private int countMyFans;
    private int countMyCollect;
    private int countMyAttention;
    private int is_attention;
    private List<ArticleInfoBean> articleInfo;

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

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public int getCountMyCircle() {
        return countMyCircle;
    }

    public void setCountMyCircle(int countMyCircle) {
        this.countMyCircle = countMyCircle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_author() {
        return is_author;
    }

    public void setIs_author(int is_author) {
        this.is_author = is_author;
    }

    public int getCountMyFans() {
        return countMyFans;
    }

    public void setCountMyFans(int countMyFans) {
        this.countMyFans = countMyFans;
    }

    public int getCountMyCollect() {
        return countMyCollect;
    }

    public void setCountMyCollect(int countMyCollect) {
        this.countMyCollect = countMyCollect;
    }

    public int getCountMyAttention() {
        return countMyAttention;
    }

    public void setCountMyAttention(int countMyAttention) {
        this.countMyAttention = countMyAttention;
    }

    public int getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public List<ArticleInfoBean> getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(List<ArticleInfoBean> articleInfo) {
        this.articleInfo = articleInfo;
    }

    public static class ArticleInfoBean {
        /**
         * article_id : 93
         * article_title : 建筑工业互联网峰会-赵伟宏
         * article_desc : 建筑工业互联网峰会-赵伟宏
         * article_pic : https://www.51jiantan.com/static/image/微信图片_20181106144207.png
         */

        private int article_id;
        private String article_title;
        private String article_desc;
        private String article_pic;

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

        public String getArticle_desc() {
            return article_desc;
        }

        public void setArticle_desc(String article_desc) {
            this.article_desc = article_desc;
        }

        public String getArticle_pic() {
            return article_pic;
        }

        public void setArticle_pic(String article_pic) {
            this.article_pic = article_pic;
        }
    }
}
