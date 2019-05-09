package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/9 10:27 AM
 * @project BuildTalk
 * @description:
 */
public class SaveRecordEntity implements Serializable {

    /**
     * guestbook_id : 177
     * user_id : 7
     * content : 测试下
     * article_id : 498
     * guestbook_time : 2019-05-09 11:08:14
     */

    private int guestbook_id;
    private int user_id;
    private String content;
    private int article_id;
    private String guestbook_time;

    public int getGuestbook_id() {
        return guestbook_id;
    }

    public void setGuestbook_id(int guestbook_id) {
        this.guestbook_id = guestbook_id;
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

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getGuestbook_time() {
        return guestbook_time;
    }

    public void setGuestbook_time(String guestbook_time) {
        this.guestbook_time = guestbook_time;
    }
}
