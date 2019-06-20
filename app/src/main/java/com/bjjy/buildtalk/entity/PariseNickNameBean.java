package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/14 3:10 PM
 * @project BuildTalk
 * @description:
 */
public class PariseNickNameBean implements Serializable {
    /**
     * name : 建谈新用户
     * user_id : 138
     * user_type : 1
     */

    private String name;
    private int user_id;
    private int user_type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
