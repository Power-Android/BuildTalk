package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/14 3:09 PM
 * @project BuildTalk
 * @description:
 */
public class CommentContentBean implements Serializable{

    /**
     * comment_id : 232
     * user_id : 145
     * content : 冥冥中
     * theme_id : 81
     * comment_time : 1560491821
     * name : 建谈新用户
     * headImage : https://www.51jiantan.com/static/image/1.jpg
     * is_author : 0
     * countpraise : 0
     * is_circleMaster : 0
     * isPraise : 0
     * guestbook_time : 2019-06-14 13:57:01
     * isDelete : 1
     */

    private int comment_id;
    private int user_id;
    private String content;
    private int theme_id;
    private int comment_time;
    private String name;
    private String headImage;
    private int is_author;
    private int countpraise;
    private int is_circleMaster;
    private int isPraise;
    private String guestbook_time;
    private int isDelete;

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

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public int getComment_time() {
        return comment_time;
    }

    public void setComment_time(int comment_time) {
        this.comment_time = comment_time;
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

    public int getIs_author() {
        return is_author;
    }

    public void setIs_author(int is_author) {
        this.is_author = is_author;
    }

    public int getCountpraise() {
        return countpraise;
    }

    public void setCountpraise(int countpraise) {
        this.countpraise = countpraise;
    }

    public int getIs_circleMaster() {
        return is_circleMaster;
    }

    public void setIs_circleMaster(int is_circleMaster) {
        this.is_circleMaster = is_circleMaster;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }

    public String getGuestbook_time() {
        return guestbook_time;
    }

    public void setGuestbook_time(String guestbook_time) {
        this.guestbook_time = guestbook_time;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
