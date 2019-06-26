package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/21 9:36 AM
 * @project BuildTalk
 * @description:
 */
public class MasterListEntity implements Serializable {

    /**
     * masterInfo : [{"author_id":10,"author_name":"张鸣","author_pic":"https://www.51jiantan.com/static/image/zhangming.png","author_desc":"民盟成员、高级工程师","author_intro":"悉尼大学工商管理硕士","sign":["平台","数据","BIM"],"education":"博士","update_pic":null,"master_pic":"https://www.51jiantan.com/static/image/zm.png","user_id":10},{"author_id":11,"author_name":"马林","author_pic":"https://www.51jiantan.com/static/image/malin.jpg","author_desc":"建筑工业化理论实践家 农房专家","author_intro":"十余年专注农房事业，被誉为\u201c中国农房第一人\u201d","sign":["农房","低层住宅"],"education":null,"update_pic":null,"master_pic":null,"user_id":147},{"author_id":18,"author_name":"苏磊","author_pic":"https://www.51jiantan.com/static/image/sulei.jpg","author_desc":"建谊副总裁","author_intro":"担任多个建筑项目总监，丰富的建筑工业互联网执行经验","sign":["建筑工业互联网"],"education":null,"update_pic":null,"master_pic":null,"user_id":149},{"author_id":20,"author_name":"张海玲","author_pic":"https://www.51jiantan.com/static/image/zhanghailing01.jpg","author_desc":"成本管理专家","author_intro":"建筑工业互联网与成本管理结合，多年从业经验","sign":["成本管理"],"education":null,"update_pic":null,"master_pic":"https://www.51jiantan.com/static/image/lr.png","user_id":146},{"author_id":21,"author_name":"赵伟宏","author_pic":"https://www.51jiantan.com/static/image/zhaoweihong.jpg","author_desc":"资源管理专家","author_intro":"地域经济互联网先行者","sign":["资源管理"],"education":null,"update_pic":null,"master_pic":"https://www.51jiantan.com/static/image/pyq.png","user_id":93},{"author_id":24,"author_name":"朱晓斌","author_pic":"https://www.51jiantan.com/static/image/zhuxiaobin.jpg","author_desc":"战略总监","author_intro":"平台普及倡导者","sign":["战略"],"education":null,"update_pic":null,"master_pic":null,"user_id":148}]
     * page : 1
     * page_count : 1
     */

    private String page;
    private int page_count;
    private List<MasterInfoBean> masterInfo;

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

    public List<MasterInfoBean> getMasterInfo() {
        return masterInfo;
    }

    public void setMasterInfo(List<MasterInfoBean> masterInfo) {
        this.masterInfo = masterInfo;
    }

    public static class MasterInfoBean {
        /**
         * author_id : 10
         * author_name : 张鸣
         * author_pic : https://www.51jiantan.com/static/image/zhangming.png
         * author_desc : 民盟成员、高级工程师
         * author_intro : 悉尼大学工商管理硕士
         * sign : ["平台","数据","BIM"]
         * education : 博士
         * update_pic : null
         * master_pic : https://www.51jiantan.com/static/image/zm.png
         * user_id : 10
         */

        private int author_id;
        private String author_name;
        private String author_pic;
        private String author_desc;
        private String author_intro;
        private String education;
        private Object update_pic;
        private String master_pic;
        private int user_id;
        private List<String> sign;

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
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

        public String getAuthor_desc() {
            return author_desc;
        }

        public void setAuthor_desc(String author_desc) {
            this.author_desc = author_desc;
        }

        public String getAuthor_intro() {
            return author_intro;
        }

        public void setAuthor_intro(String author_intro) {
            this.author_intro = author_intro;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public Object getUpdate_pic() {
            return update_pic;
        }

        public void setUpdate_pic(Object update_pic) {
            this.update_pic = update_pic;
        }

        public String getMaster_pic() {
            return master_pic;
        }

        public void setMaster_pic(String master_pic) {
            this.master_pic = master_pic;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public List<String> getSign() {
            return sign;
        }

        public void setSign(List<String> sign) {
            this.sign = sign;
        }
    }
}
