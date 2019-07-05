package com.bjjy.buildtalk.core.event;

/**
 * @author power
 * @date 2019/7/4 5:17 PM
 * @project BuildTalk
 * @description:
 */
public class PayEvent {
    private String str_msg;
    private int int_msg;

    public PayEvent(String msg) {
        this.str_msg = msg;
    }

    public PayEvent(int msg) {
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
