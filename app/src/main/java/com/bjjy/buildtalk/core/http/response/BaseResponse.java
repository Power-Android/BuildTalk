package com.bjjy.buildtalk.core.http.response;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public class BaseResponse<T> {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    /**
     * 0：成功，1：失败
     */
    private int errorCode;

    private String msg;

    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return msg;
    }

    public void setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
