package com.bjjy.buildtalk.utils;

import android.text.TextUtils;

import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.entity.SongsEntity;

/**
 * @author power
 * @date 2019-12-06 14:07
 * @project BuildTalk
 * @description:
 */
public class PlayerHelper {

    /**
     * @param article_id
     * @param dataManager
     * 点击item前查询数据库是否有数据
     */
    public static SongsEntity querySongs(String article_id, DataManager dataManager) {
        SongsEntity songsEntity = dataManager.querySongsDataById(article_id);
        if (songsEntity != null && !TextUtils.isEmpty(songsEntity.getAudio_url())){
            return songsEntity;
        }
        return null;
    }
}
