package com.bjjy.buildtalk.core.event;

import com.bjjy.buildtalk.entity.SongsEntity;

import java.util.List;

/**
 * @author power
 * @date 2019-12-05 11:40
 * @project BuildTalk
 * @description:
 */
public class PlayerEvent {
    private String str_msg;
    private int int_msg;

    public PlayerEvent(String msg) {
        this.str_msg = msg;
    }

    public PlayerEvent(int msg) {
        this.int_msg = msg;
    }

    public String getMsg() {
        return str_msg;
    }

    public void setMsg(String msg) {
        this.str_msg = msg;
    }

    public int getInt_msg() {
        return int_msg;
    }

    public void setInt_msg(int int_msg) {
        this.int_msg = int_msg;
    }
}
