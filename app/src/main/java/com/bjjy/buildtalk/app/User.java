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
    private String bindStatus;
    private boolean loginStatus;

    public User() {
    }

    @Generated(hash = 2097022275)
    public User(Long id, String user_id, String headImage, String nickName,
            String mobile, String bindStatus, boolean loginStatus) {
        this.id = id;
        this.user_id = user_id;
        this.headImage = headImage;
        this.nickName = nickName;
        this.mobile = mobile;
        this.bindStatus = bindStatus;
        this.loginStatus = loginStatus;
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
