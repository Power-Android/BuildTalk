package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/5/7 1:52 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkDetailEntity implements Serializable {

    /**
     * newsInfo : {"article_id":89,"article_title":"建筑工业互联网峰会-杨海潮","audio_id":35,"video_id":88,"content":"<p>建筑工业互联网峰会-杨海潮<\/p>","publish_time":"2018-11-06 14:34:35","author_id":17,"audio_url":"https://www.51jiantan.com/static/audio/1227.mp3","media_type":3,"audio_read":"董坤","audio_duration":"242","audio_size":"3.72","video_url":"http://mp4.chinabim.com/07-Ñîº£³±ÑÝ½².mp4","course_id":0,"isCollect":0,"countCollect":0,"author_name":"杨海潮","author_pic":"https://www.51jiantan.com/static/image/yanghaichao.jpg"}
     * guestbookInfo : [{"guestbook_id":100,"user_id":24,"content":"智慧城市需要智能家居吧，方便洽谈一下合作吗","article_id":89,"guestbook_time":"2018-11-16 17:53:11","headimage":"https://wx.qlogo.cn/mmopen/vi_32/l9vY4SPbAKegKetuYRU9FDJu6W7Ac38Ngj5MSpJicHsKdxuniadLRnvV2KUnBhhbHfDVvpY8iaGgrMxiaws1X5G8Ag/132","nickname":"就你最近的表现","countpraise":0,"isPraise":0},{"guestbook_id":97,"user_id":47,"content":"这个GIS是什么？谁知道？","article_id":89,"guestbook_time":"2018-11-16 17:51:51","headimage":"https://wx.qlogo.cn/mmopen/vi_32/haxtRyFofTlLtpSdoOWicjs0pNKiavFXHCLY0B8HsZJ6iasThcEtQD9wiazMvBBpQrU4m3Ozx0j6lYjbJTZqUBiaCSA/132","nickname":"小地瓜","countpraise":0,"isPraise":0},{"guestbook_id":87,"user_id":37,"content":"关注政府联合审批系统\u2026\u2026、","article_id":89,"guestbook_time":"2018-11-16 17:37:44","headimage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL6Pq7awDl4WDUzYQiciahrmoxbrmiamdT0VkmWct8tic7bmIZOicQo9GDAibJNxzVNSmAJvMvdlZeuo8mA/132","nickname":"我是人见人爱花见花开的仙女钢镚啊","countpraise":0,"isPraise":0},{"guestbook_id":84,"user_id":42,"content":"为政府搭建的这个多规合一联合审批系统啥时候开始启用，希望能尽快落地。","article_id":89,"guestbook_time":"2018-11-16 17:28:42","headimage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKalOq2Wb8FwoiaYHiakW0HhUz8UMqnfgiafrSchIErVoWIEsRUCZZ8sCKvaUR1zswKOwrjAia5AOjDrg/132","nickname":"小怡","countpraise":0,"isPraise":0}]
     */

    private NewsInfoBean newsInfo;
    private List<GuestbookInfoBean> guestbookInfo;

    public NewsInfoBean getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(NewsInfoBean newsInfo) {
        this.newsInfo = newsInfo;
    }

    public List<GuestbookInfoBean> getGuestbookInfo() {
        return guestbookInfo;
    }

    public void setGuestbookInfo(List<GuestbookInfoBean> guestbookInfo) {
        this.guestbookInfo = guestbookInfo;
    }

    public static class NewsInfoBean {
        /**
         * article_id : 89
         * article_title : 建筑工业互联网峰会-杨海潮
         * audio_id : 35
         * video_id : 88
         * content : <p>建筑工业互联网峰会-杨海潮</p>
         * publish_time : 2018-11-06 14:34:35
         * author_id : 17
         * audio_url : https://www.51jiantan.com/static/audio/1227.mp3
         * media_type : 3
         * audio_read : 董坤
         * audio_duration : 242
         * audio_size : 3.72
         * video_url : http://mp4.chinabim.com/07-Ñîº£³±ÑÝ½².mp4
         * course_id : 0
         * isCollect : 0
         * countCollect : 0
         * author_name : 杨海潮
         * author_pic : https://www.51jiantan.com/static/image/yanghaichao.jpg
         */

        private int article_id;
        private String article_title;
        private int audio_id;
        private int video_id;
        private String content;
        private String publish_time;
        private int author_id;
        private String audio_url;
        private int media_type;
        private String audio_read;
        private String audio_duration;
        private String audio_size;
        private String video_url;
        private int course_id;
        private int isCollect;
        private int countCollect;
        private String author_name;
        private String author_pic;

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public int getAudio_id() {
            return audio_id;
        }

        public void setAudio_id(int audio_id) {
            this.audio_id = audio_id;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public String getAudio_url() {
            return audio_url;
        }

        public void setAudio_url(String audio_url) {
            this.audio_url = audio_url;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public String getAudio_read() {
            return audio_read;
        }

        public void setAudio_read(String audio_read) {
            this.audio_read = audio_read;
        }

        public String getAudio_duration() {
            return audio_duration;
        }

        public void setAudio_duration(String audio_duration) {
            this.audio_duration = audio_duration;
        }

        public String getAudio_size() {
            return audio_size;
        }

        public void setAudio_size(String audio_size) {
            this.audio_size = audio_size;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getCountCollect() {
            return countCollect;
        }

        public void setCountCollect(int countCollect) {
            this.countCollect = countCollect;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_pic() {
            return author_pic;
        }

        public void setAuthor_pic(String author_pic) {
            this.author_pic = author_pic;
        }
    }

    public static class GuestbookInfoBean {
        /**
         * guestbook_id : 100
         * user_id : 24
         * content : 智慧城市需要智能家居吧，方便洽谈一下合作吗
         * article_id : 89
         * guestbook_time : 2018-11-16 17:53:11
         * headimage : https://wx.qlogo.cn/mmopen/vi_32/l9vY4SPbAKegKetuYRU9FDJu6W7Ac38Ngj5MSpJicHsKdxuniadLRnvV2KUnBhhbHfDVvpY8iaGgrMxiaws1X5G8Ag/132
         * nickname : 就你最近的表现
         * countpraise : 0
         * isPraise : 0
         */

        private int guestbook_id;
        private int user_id;
        private String content;
        private int article_id;
        private String guestbook_time;
        private String headimage;
        private String nickname;
        private int countpraise;
        private int isPraise;

        public int getGuestbook_id() {
            return guestbook_id;
        }

        public void setGuestbook_id(int guestbook_id) {
            this.guestbook_id = guestbook_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getGuestbook_time() {
            return guestbook_time;
        }

        public void setGuestbook_time(String guestbook_time) {
            this.guestbook_time = guestbook_time;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getCountpraise() {
            return countpraise;
        }

        public void setCountpraise(int countpraise) {
            this.countpraise = countpraise;
        }

        public int getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(int isPraise) {
            this.isPraise = isPraise;
        }
    }
}
