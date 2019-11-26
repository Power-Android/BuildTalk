package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019-11-19 10:01
 * @project BuildTalk
 * @description:
 */
public class DissertationListEntity implements Serializable {

    /**
     * dissertationInfo : [{"dissertation_id":4,"dissertation_title":"2019钢结构装配式住宅技术体系研讨暨装配式装饰装修工程交流会","dissertation_pic":"3129","type":2,"copyrightYear":"2019","pic_url":"https://www.51jiantan.com/static/image/zhuangshizhuangxiu.jpg?id=1"},{"dissertation_id":3,"dissertation_title":"2019装配式建筑技术交流及融合发展论坛","dissertation_pic":"3100","type":2,"copyrightYear":"2019","pic_url":"https://jt.chinabim.com/static/image/zhuangpeishigangjiegou.jpg?id=1"},{"dissertation_id":1,"dissertation_title":"2018年中国数字产业峰会","dissertation_pic":"21","type":1,"copyrightYear":"2018","pic_url":"https://jt.chinabim.com/static/image//banner2.png"}]
     * page : 1
     * page_size : 10
     */

    private int page;
    private int page_count;
    private List<DissertationInfoBean> dissertationInfo;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public List<DissertationInfoBean> getDissertationInfo() {
        return dissertationInfo;
    }

    public void setDissertationInfo(List<DissertationInfoBean> dissertationInfo) {
        this.dissertationInfo = dissertationInfo;
    }

    public static class DissertationInfoBean {
        /**
         * dissertation_id : 4
         * dissertation_title : 2019钢结构装配式住宅技术体系研讨暨装配式装饰装修工程交流会
         * dissertation_pic : 3129
         * type : 2
         * copyrightYear : 2019
         * pic_url : https://www.51jiantan.com/static/image/zhuangshizhuangxiu.jpg?id=1
         */

        private int dissertation_id;
        private String dissertation_title;
        private String dissertation_pic;
        private int type;
        private String copyrightYear;
        private String pic_url;

        public int getDissertation_id() {
            return dissertation_id;
        }

        public void setDissertation_id(int dissertation_id) {
            this.dissertation_id = dissertation_id;
        }

        public String getDissertation_title() {
            return dissertation_title;
        }

        public void setDissertation_title(String dissertation_title) {
            this.dissertation_title = dissertation_title;
        }

        public String getDissertation_pic() {
            return dissertation_pic;
        }

        public void setDissertation_pic(String dissertation_pic) {
            this.dissertation_pic = dissertation_pic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCopyrightYear() {
            return copyrightYear;
        }

        public void setCopyrightYear(String copyrightYear) {
            this.copyrightYear = copyrightYear;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }
}
