package com.bjjy.buildtalk.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author power
 * @date 2019-12-05 14:03
 * @project BuildTalk
 * @description:
 */

@Entity
public class SongsEntity {

    private String article_id;
    private String audio_url;
    private String article_title;
    private long duration;
    private String audio_picUrl;
    private long currentTime; //歌曲播放时长位置
    private int position;//在音乐列表的位置

    @Generated(hash = 1286540935)
    public SongsEntity(String article_id, String audio_url, String article_title,
            long duration, String audio_picUrl, long currentTime, int position) {
        this.article_id = article_id;
        this.audio_url = audio_url;
        this.article_title = article_title;
        this.duration = duration;
        this.audio_picUrl = audio_picUrl;
        this.currentTime = currentTime;
        this.position = position;
    }

    @Generated(hash = 1036998878)
    public SongsEntity() {
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAudio_picUrl() {
        return audio_picUrl;
    }

    public void setAudio_picUrl(String audio_picUrl) {
        this.audio_picUrl = audio_picUrl;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
