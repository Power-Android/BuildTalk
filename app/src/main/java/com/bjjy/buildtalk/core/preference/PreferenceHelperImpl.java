package com.bjjy.buildtalk.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.entity.SongsEntity;

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
    public void setIsGuide(boolean isGuide) {
        mPreferences.edit().putBoolean(Constants.ISGUIDE, isGuide).apply();
    }

    @Override
    public boolean getIsGuide() {
        return mPreferences.getBoolean(Constants.ISGUIDE,false);
    }

    @Override
    public void setIsSHowPlayer(boolean isShow) {
        mPreferences.edit().putBoolean(Constants.IS_SHOW_PLAYER, isShow).apply();
    }

    @Override
    public boolean getIsShowPlayer() {
        return mPreferences.getBoolean(Constants.IS_SHOW_PLAYER, false);
    }

    @Override
    public void setHistorySongsData(String songsId) {
        mPreferences.edit().putString(Constants.SONG_ID, songsId).apply();
    }

    @Override
    public String getHistorySongsData() {
        return mPreferences.getString(Constants.SONG_ID, "");
    }
}
