package com.bjjy.buildtalk.core.preference;

import com.bjjy.buildtalk.entity.SongsEntity;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public interface PreferenceHelper {
    void setIsGuide(boolean isGuide);
    boolean getIsGuide();

    void setIsSHowPlayer(boolean isShow);
    boolean getIsShowPlayer();

    void setHistorySongsData(String  songsId);
    String getHistorySongsData();
}
