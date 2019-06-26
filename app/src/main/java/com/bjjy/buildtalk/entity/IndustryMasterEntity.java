package com.bjjy.buildtalk.entity;

import java.util.List;

/**
 * @author power
 * @date 2019/6/20 10:37 AM
 * @project BuildTalk
 * @description:
 */
public class IndustryMasterEntity {

    /**
     * page : 1
     * page_count : 1
     * masterInfo : [{"author_id":20,"master_pic":"https://www.51jiantan.com/static/image/lr.png","user_id":146},{"author_id":10,"master_pic":"https://www.51jiantan.com/static/image/zm.png","user_id":10},{"author_id":21,"master_pic":"https://www.51jiantan.com/static/image/pyq.png","user_id":93}]
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
         * author_id : 20
         * master_pic : https://www.51jiantan.com/static/image/lr.png
         * user_id : 146
         */

        private int author_id;
        private String master_pic;
        private int user_id;

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
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
    }
}
