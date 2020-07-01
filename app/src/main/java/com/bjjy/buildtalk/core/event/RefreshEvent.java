package com.bjjy.buildtalk.core.event;

/**
 * @author power
 * @date 2019/6/17 11:33 AM
 * @project BuildTalk
 * @description:
 */
public class RefreshEvent {
    private String str_msg;
    private int int_msg;
    private int position;

    public RefreshEvent(String msg) {
        this.str_msg = msg;
    }

    public RefreshEvent(int msg) {
        this.int_msg = msg;
    }

    public RefreshEvent(String msg, int position){
        this.str_msg = msg;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
