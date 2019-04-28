package com.bjjy.buildtalk.core.preference;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public interface PreferenceHelper {
    void setLoginStatus(boolean isLogin);
    boolean getLoginStatus();

    void setLoginAccount(String account);
    String getLoginAccount();

}
