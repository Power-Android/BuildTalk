package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2020/6/1 2:11 PM
 * @project BuildTalk
 * @description:
 */
public class ThemeVideoBean implements Serializable {

    /**
     * video_id : 251
     * video_url : http://1252135833.vod2.myqcloud.com/48ee1223vodcq1252135833/85973cdc5285890803201102575/iogoV5NshBwA.mp4
     * video_duration : 7
     * coverURL :
     * tx_videoId :
     */

    private int video_id;
    private String video_url;
    private String video_duration;
    private String coverURL;
    private String tx_videoId;
    private String video_height;
    private String video_width;

    public String getVideo_height() {
        return video_height;
    }

    public void setVideo_height(String video_height) {
        this.video_height = video_height;
    }

    public String getVideo_width() {
        return video_width;
    }

    public void setVideo_width(String video_width) {
        this.video_width = video_width;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getTx_videoId() {
        return tx_videoId;
    }

    public void setTx_videoId(String tx_videoId) {
        this.tx_videoId = tx_videoId;
    }
}
