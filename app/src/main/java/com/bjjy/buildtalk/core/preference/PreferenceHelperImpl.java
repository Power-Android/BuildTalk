package com.bjjy.buildtalk.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public class PreferenceHelperImpl implements PreferenceHelper {
    private final SharedPreferences mPreferences;
    @Inject
    PreferenceHelperImpl() {
        mPreferences = App.getContext().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferences.edit().putString(Constants.ACCOUNT, account).apply();
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    @Override
    public void setIsGuide(boolean isGuide) {
        mPreferences.edit().putBoolean(Constants.ISGUIDE, isGuide).apply();
    }

    @Override
    public boolean getIsGuide() {
        return mPreferences.getBoolean(Constants.ISGUIDE,false);
    }

}
