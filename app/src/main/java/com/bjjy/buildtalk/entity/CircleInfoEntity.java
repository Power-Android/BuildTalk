package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/3 3:01 PM
 * @project BuildTalk
 * @description:
 */
public class CircleInfoEntity implements Serializable {

    /**
     * isJoin : 0
     * circleInfo : {"circle_id":3,"user_id":10,"circle_image":{"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null},"circle_name":"天的一二三","circle_tags":["BIM","钢结构","装配式","一体化","自然会"],"circle_desc":"建筑产业工业互联网平台","type":2,"data_id":10,"name":"张鸣","is_author":1,"typeName":"课程","create_day":44,"course":{"course_desc":"Revit课程","countCourse":40,"updateCourse":20,"course_title":"专业Revit课程1","is_audition":1,"audio_url":null,"video_url":"http://www.chinabim.com/curriculumResources/teachVideo/1/20180330/506c7e6099ab4200b7e9363458dd2794source.mp4","media_id":2}}
     * themeInfo : [{"theme_id":2,"theme_content":"发布主题测试222","theme_image":[{"pic_id":"","pic_url":null}],"publish_time":"2019-03-27 10:43:49","user_id":4,"circle_id":3,"name":"♞木有年輪","is_circleMaster":0},{"theme_id":1,"theme_content":"发布主题测试111","theme_image":[{"pic_id":"27","pic_url":"https://www.51jiantan.com/static/image/0069kQGBgy1ft8at9h5goj33vc2kw4qw.jpg"},{"pic_id":"28","pic_url":"https://www.51jiantan.com/static/image/71b2cbb3ly1ftiy26f7ujj21kw2dcu1b.jpg"}],"publish_time":"2019-03-27 10:42:15","user_id":4,"circle_id":3,"name":"♞木有年輪","is_circleMaster":0}]
     */

    private String isJoin;
    private int countChoiceness_themeInfo;
    private CircleInfoBean circleInfo;
    private List<ThemeInfoBean> themeInfo;

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public int getCountChoiceness_themeInfo() {
        return countChoiceness_themeInfo;
    }

    public void setCountChoiceness_themeInfo(int countChoiceness_themeInfo) {
        this.countChoiceness_themeInfo = countChoiceness_themeInfo;
    }

    public CircleInfoBean getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(CircleInfoBean circleInfo) {
        this.circleInfo = circleInfo;
    }

    public List<ThemeInfoBean> getThemeInfo() {
        return themeInfo;
    }

    public void setThemeInfo(List<ThemeInfoBean> themeInfo) {
        this.themeInfo = themeInfo;
    }

    public static class CircleInfoBean {
        /**
         * circle_id : 3
         * user_id : 10
         * circle_image : {"pic_id":20,"pic_url":"https://www.51jiantan.com/static/image/2.jpg","savetime":null}
         * circle_name : 天的一二三
         * circle_tags : ["BIM","钢结构","装配式","一体化","自然会"]
         * circle_desc : 建筑产业工业互联网平台
         * type : 2
         * data_id : 10
         * name : 张鸣
         * is_author : 1
         * typeName : 课程
         * create_day : 44
         * course : {"course_desc":"Revit课程","countCourse":40,"updateCourse":20,"course_title":"专业Revit课程1","is_audition":1,"audio_url":null,"video_url":"http://www.chinabim.com/curriculumResources/teachVideo/1/20180330/506c7e6099ab4200b7e9363458dd2794source.mp4","media_id":2}
         */

        private int circle_id;
        private int user_id;
        private CircleImageBean circle_image;
        private String circle_name;
        private String circle_desc;
        private int type;
        private int data_id;
        private String name;
        private int is_author;
        private String typeName;
        private int create_day;
        private CourseBean course;
        private List<String> circle_tags;
        private String master_pic;
        private String course_money;
        private String lightSpot;
        private String course_desc;
        private String pay_desc;
        private int course_id;
        private int joinCirlceNum;

        public String getCourse_desc() {
            return course_desc;
        }

        public void setCourse_desc(String course_desc) {
            this.course_desc = course_desc;
        }

        public String getPay_desc() {
            return pay_desc;
        }

        public void setPay_desc(String pay_desc) {
            this.pay_desc = pay_desc;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getJoinCirlceNum() {
            return joinCirlceNum;
        }

        public void setJoinCirlceNum(int joinCirlceNum) {
            this.joinCirlceNum = joinCirlceNum;
        }

        public String getLightSpot() {
            return lightSpot;
        }

        public void setLightSpot(String lightSpot) {
            this.lightSpot = lightSpot;
        }

        public String getCourse_money() {
            return course_money;
        }

        public void setCourse_money(String course_money) {
            this.course_money = course_money;
        }

        public String getMaster_pic() {
            return master_pic;
        }

        public void setMaster_pic(String master_pic) {
            this.master_pic = master_pic;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public CircleImageBean getCircle_image() {
            return circle_image;
        }

        public void setCircle_image(CircleImageBean circle_image) {
            this.circle_image = circle_image;
        }

        public String getCircle_name() {
            return circle_name;
        }

        public void setCircle_name(String circle_name) {
            this.circle_name = circle_name;
        }

        public String getCircle_desc() {
            return circle_desc;
        }

        public void setCircle_desc(String circle_desc) {
            this.circle_desc = circle_desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getData_id() {
            return data_id;
        }

        public void setData_id(int data_id) {
            this.data_id = data_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getCreate_day() {
            return create_day;
        }

        public void setCreate_day(int create_day) {
            this.create_day = create_day;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public List<String> getCircle_tags() {
            return circle_tags;
        }

        public void setCircle_tags(List<String> circle_tags) {
            this.circle_tags = circle_tags;
        }

        public static class CircleImageBean {
            /**
             * pic_id : 20
             * pic_url : https://www.51jiantan.com/static/image/2.jpg
             * savetime : null
             */

            private String pic_id;
            private String pic_url;
            private String savetime;

            public String getPic_id() {
                return pic_id;
            }

            public void setPic_id(String pic_id) {
                this.pic_id = pic_id;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public String getSavetime() {
                return savetime;
            }

            public void setSavetime(String savetime) {
                this.savetime = savetime;
            }
        }

        public static class CourseBean {
            /**
             * course_desc : Revit课程
             * countCourse : 40
             * updateCourse : 20
             * course_title : 专业Revit课程1
             * is_audition : 1
             * audio_url : null
             * video_url : http://www.chinabim.com/curriculumResources/teachVideo/1/20180330/506c7e6099ab4200b7e9363458dd2794source.mp4
             * media_id : 2
             */

            private String course_desc;
            private int countCourse;
            private int updateCourse;
            private String course_title;
            private int is_audition;
            private Object audio_url;
            private String video_url;
            private int media_id;

            public String getCourse_desc() {
                return course_desc;
            }

            public void setCourse_desc(String course_desc) {
                this.course_desc = course_desc;
            }

            public int getCountCourse() {
                return countCourse;
            }

            public void setCountCourse(int countCourse) {
                this.countCourse = countCourse;
            }

            public int getUpdateCourse() {
                return updateCourse;
            }

            public void setUpdateCourse(int updateCourse) {
                this.updateCourse = updateCourse;
            }

            public String getCourse_title() {
                return course_title;
            }

            public void setCourse_title(String course_title) {
                this.course_title = course_title;
            }

            public int getIs_audition() {
                return is_audition;
            }

            public void setIs_audition(int is_audition) {
                this.is_audition = is_audition;
            }

            public Object getAudio_url() {
                return audio_url;
            }

            public void setAudio_url(Object audio_url) {
                this.audio_url = audio_url;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public int getMedia_id() {
                return media_id;
            }

            public void setMedia_id(int media_id) {
                this.media_id = media_id;
            }
        }
    }

    public static class ThemeInfoBean {
        /**
         * theme_id : 2
         * theme_content : 发布主题测试222
         * theme_image : [{"pic_id":"","pic_url":null}]
         * publish_time : 2019-03-27 10:43:49
         * user_id : 4
         * circle_id : 3
         * name : ♞木有年輪
         * is_circleMaster : 0
         */

        private int theme_id;
        private String theme_content;
        private String publish_time;
        private int user_id;
        private int circle_id;
        private String name;
        private int is_circleMaster;
        private List<ThemeImageBean> theme_image;

        public int getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(int theme_id) {
            this.theme_id = theme_id;
        }

        public String getTheme_content() {
            return theme_content;
        }

        public void setTheme_content(String theme_content) {
            this.theme_content = theme_content;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_circleMaster() {
            return is_circleMaster;
        }

        public void setIs_circleMaster(int is_circleMaster) {
            this.is_circleMaster = is_circleMaster;
        }

        public List<ThemeImageBean> getTheme_image() {
            return theme_image;
        }

        public void setTheme_image(List<ThemeImageBean> theme_image) {
            this.theme_image = theme_image;
        }

        public static class ThemeImageBean {
            /**
             * pic_id :
             * pic_url : null
             */

            private String pic_id;
            private Object pic_url;

            public String getPic_id() {
                return pic_id;
            }

            public void setPic_id(String pic_id) {
                this.pic_id = pic_id;
            }

            public Object getPic_url() {
                return pic_url;
            }

            public void setPic_url(Object pic_url) {
                this.pic_url = pic_url;
            }
        }
    }
}
