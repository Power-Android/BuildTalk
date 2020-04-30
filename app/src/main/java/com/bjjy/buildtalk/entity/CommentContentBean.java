package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

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
    private int reply_commentId;//回复的评论id
    private int reply_isCircleMaster;//被回复的用户是否是圈主 0不是 1是
    private String reply_name;//被回复的用户name
    private int reply_isAuthor;//被回复的用户是否是大咖  0不是 1是
    private int reply_userId; //被回复的用户id
    private int comment_type; //类型  1普通评论  2回复评论
    private String parentCommentId;

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    private List<ReplyCommentInfoBean> reply_commentInfo;

    public List<ReplyCommentInfoBean> getReply_commentInfo() {
        return reply_commentInfo;
    }

    public void setReply_commentInfo(List<ReplyCommentInfoBean> reply_commentInfo) {
        this.reply_commentInfo = reply_commentInfo;
    }

    public static class ReplyCommentInfoBean implements Serializable{

        /**
         * comment_id : 10
         * user_id : 261
         * content : 的网线
         * reply_commentId : 14
         * theme_id : 55
         * comment_time : 1565591467
         * source : android
         * status : 1
         * is_author : 0
         * name : 奈文摩尔
         * is_circleMaster : 1
         * reply_isCircleMaster : 1
         * reply_name : 奈文摩尔
         * reply_isAuthor : 0
         * reply_userId : 261
         * comment_type : 2
         * guestbook_time : 2019-08-12 14:31:07
         */

        private int comment_id;
        private int user_id;
        private String content;
        private int reply_commentId;
        private int theme_id;
        private int comment_time;
        private String source;
        private int status;
        private int is_author;
        private String name;
        private int is_circleMaster;
        private int reply_isCircleMaster;
        private String reply_name;
        private int reply_isAuthor;
        private int reply_userId;
        private int comment_type;
        private String guestbook_time;
        private String parentCommentId;

        public String getParentCommentId() {
            return parentCommentId;
        }

        public void setParentCommentId(String parentCommentId) {
            this.parentCommentId = parentCommentId;
        }

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

        public int getReply_commentId() {
            return reply_commentId;
        }

        public void setReply_commentId(int reply_commentId) {
            this.reply_commentId = reply_commentId;
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_circleMaster() {
            return is_circleMaster;
        }

        public void setIs_circleMaster(int is_circleMaster) {
            this.is_circleMaster = is_circleMaster;
        }

        public int getReply_isCircleMaster() {
            return reply_isCircleMaster;
        }

        public void setReply_isCircleMaster(int reply_isCircleMaster) {
            this.reply_isCircleMaster = reply_isCircleMaster;
        }

        public String getReply_name() {
            return reply_name;
        }

        public void setReply_name(String reply_name) {
            this.reply_name = reply_name;
        }

        public int getReply_isAuthor() {
            return reply_isAuthor;
        }

        public void setReply_isAuthor(int reply_isAuthor) {
            this.reply_isAuthor = reply_isAuthor;
        }

        public int getReply_userId() {
            return reply_userId;
        }

        public void setReply_userId(int reply_userId) {
            this.reply_userId = reply_userId;
        }

        public int getComment_type() {
            return comment_type;
        }

        public void setComment_type(int comment_type) {
            this.comment_type = comment_type;
        }

        public String getGuestbook_time() {
            return guestbook_time;
        }

        public void setGuestbook_time(String guestbook_time) {
            this.guestbook_time = guestbook_time;
        }
    }

    public int getReply_commentId() {
        return reply_commentId;
    }

    public void setReply_commentId(int reply_commentId) {
        this.reply_commentId = reply_commentId;
    }

    public int getReply_isCircleMaster() {
        return reply_isCircleMaster;
    }

    public void setReply_isCircleMaster(int reply_isCircleMaster) {
        this.reply_isCircleMaster = reply_isCircleMaster;
    }

    public String getReply_name() {
        return reply_name;
    }

    public void setReply_name(String reply_name) {
        this.reply_name = reply_name;
    }

    public int getReply_isAuthor() {
        return reply_isAuthor;
    }

    public void setReply_isAuthor(int reply_isAuthor) {
        this.reply_isAuthor = reply_isAuthor;
    }

    public int getReply_userId() {
        return reply_userId;
    }

    public void setReply_userId(int reply_userId) {
        this.reply_userId = reply_userId;
    }

    public int getComment_type() {
        return comment_type;
    }

    public void setComment_type(int comment_type) {
        this.comment_type = comment_type;
    }

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
