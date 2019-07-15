package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/7/1 11:29 AM
 * @project BuildTalk
 * @description:
 */
public class CourseListEntity implements Serializable {

    /**
     * page : 1
     * page_count : 4
     * countCourse : 40
     * countUpdateCourse : 20
     * courselist : [{"article_id":11,"article_title":"ArchiCAD课程1","article_desc":"ArchiCAD课程1","course_id":9,"is_audition":1,"audio_id":null,"audioInfo":null,"video_id":"1","content":"<p><span style=\"font-family: 宋体; font-size: 15px; background-color: rgb","media_id":2,"videoInfo":{"video_id":39,"video_url":"http://www.chinabim.com/teachonline/teach/courseStudy/goCurriculumDetail?curriculumId=142&TEACH_VIDEO"}}]
     */

    private String page;
    private int page_count;
    private int countCourse;
    private int countUpdateCourse;
    private List<CourselistBean> courselist;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getCountCourse() {
        return countCourse;
    }

    public void setCountCourse(int countCourse) {
        this.countCourse = countCourse;
    }

    public int getCountUpdateCourse() {
        return countUpdateCourse;
    }

    public void setCountUpdateCourse(int countUpdateCourse) {
        this.countUpdateCourse = countUpdateCourse;
    }

    public List<CourselistBean> getCourselist() {
        return courselist;
    }

    public void setCourselist(List<CourselistBean> courselist) {
        this.courselist = courselist;
    }

    public static class CourselistBean implements Serializable{
        /**
         * article_id : 11
         * article_title : ArchiCAD课程1
         * article_desc : ArchiCAD课程1
         * course_id : 9
         * is_audition : 1
         * audio_id : null
         * audioInfo : null
         * video_id : 1
         * content : <p><span style="font-family: 宋体; font-size: 15px; background-color: rgb
         * media_id : 2
         * videoInfo : {"video_id":39,"video_url":"http://www.chinabim.com/teachonline/teach/courseStudy/goCurriculumDetail?curriculumId=142&TEACH_VIDEO"}
         */

        private int article_id;
        private String article_title;
        private String article_desc;
        private int course_id;
        private int is_audition;
        private Object audio_id;
        private Object audioInfo;
        private String video_id;
        private String content;
        private int media_id;
        private VideoInfoBean videoInfo;

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

        public String getArticle_desc() {
            return article_desc;
        }

        public void setArticle_desc(String article_desc) {
            this.article_desc = article_desc;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getIs_audition() {
            return is_audition;
        }

        public void setIs_audition(int is_audition) {
            this.is_audition = is_audition;
        }

        public Object getAudio_id() {
            return audio_id;
        }

        public void setAudio_id(Object audio_id) {
            this.audio_id = audio_id;
        }

        public Object getAudioInfo() {
            return audioInfo;
        }

        public void setAudioInfo(Object audioInfo) {
            this.audioInfo = audioInfo;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMedia_id() {
            return media_id;
        }

        public void setMedia_id(int media_id) {
            this.media_id = media_id;
        }

        public VideoInfoBean getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(VideoInfoBean videoInfo) {
            this.videoInfo = videoInfo;
        }

        public static class VideoInfoBean implements Serializable{
            /**
             * video_id : 39
             * video_url : http://www.chinabim.com/teachonline/teach/courseStudy/goCurriculumDetail?curriculumId=142&TEACH_VIDEO
             */

            private int video_id;
            private String video_url;

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
        }
    }
}
