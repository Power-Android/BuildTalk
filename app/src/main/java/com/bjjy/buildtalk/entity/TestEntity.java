package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/4/29 11:20 AM
 * @project BuildTalk
 * @description:
 */
public class TestEntity implements Serializable{


    /**
     * author_name : 张鸣
     */

    private String author_name;

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

}
