package com.bjjy.buildtalk.app;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;


/**
 * @author power
 * @date 2019/4/25 2:35 PM
 * @project BuildTalk
 * @description:
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String user_id;
    private String headImage;
    private String nickName;
    private String mobile;
    private String user_type;
    private String updateStatus;
    private String bindStatus;
    private String bg_pic;
    private boolean loginStatus;
    private int countCheckRecord;

    public User() {
    }

    @Generated(hash = 782017750)
    public User(Long id, String user_id, String headImage, String nickName,
            String mobile, String user_type, String updateStatus, String bindStatus,
            String bg_pic, boolean loginStatus, int countCheckRecord) {
        this.id = id;
        this.user_id = user_id;
        this.headImage = headImage;
        this.nickName = nickName;
        this.mobile = mobile;
        this.user_type = user_type;
        this.updateStatus = updateStatus;
        this.bindStatus = bindStatus;
        this.bg_pic = bg_pic;
        this.loginStatus = loginStatus;
        this.countCheckRecord = countCheckRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headimage) {
        this.headImage = headimage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickname) {
        this.nickName = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean getLoginStatus() {
        return this.loginStatus;
    }

    public int getCountCheckRecord() {
        return countCheckRecord;
    }

    public void setCountCheckRecord(int countCheckRecord) {
        this.countCheckRecord = countCheckRecord;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", headImage='" + headImage + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", bindStatus='" + bindStatus + '\'' +
                ", loginStatus=" + loginStatus +
                '}';
    }
}
