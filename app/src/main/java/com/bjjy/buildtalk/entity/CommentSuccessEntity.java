package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/14 3:12 PM
 * @project BuildTalk
 * @description:
 */
public class CommentSuccessEntity implements Serializable {

    /**
     * commentInfo : [{"comment_id":232,"user_id":145,"content":"冥冥中","theme_id":81,"comment_time":1560491821,"name":"建谈新用户","headImage":"https://www.51jiantan.com/static/image/1.jpg","is_author":0,"countpraise":0,"is_circleMaster":0,"isPraise":0,"guestbook_time":"2019-06-14 13:57:01","isDelete":1}]
     * page : 1
     * page_count : 1
     * countCommentNum : 1
     */

    private int page;
    private int page_count;
    private int countCommentNum;
    private List<CommentContentBean> comment_content;

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

    public List<CommentContentBean> getCommentInfo() {
        return comment_content;
    }

    public void setCommentInfo(List<CommentContentBean> comment_content) {
        this.comment_content = comment_content;
    }

}
