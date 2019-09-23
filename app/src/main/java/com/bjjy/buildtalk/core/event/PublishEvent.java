package com.bjjy.buildtalk.core.event;

/**
 * @author power
 * @date 2019-09-19 10:11
 * @project BuildTalk
 * @description:
 */
public class PublishEvent {
    private String type;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PublishEvent(String type, String url) {
        this.type = type;
        this.url = url;
    }
}
