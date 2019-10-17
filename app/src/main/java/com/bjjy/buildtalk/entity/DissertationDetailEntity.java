package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019-09-16 11:16
 * @project BuildTalk
 * @description:
 */
public class DissertationDetailEntity implements Serializable {

    /**
     * dissertation_id : 2
     * dissertation_title : 2019年中国数字产业峰会
     * dissertation_desc : <p><span data-v-7b8e67be="" class="tipsBoxspan">工业互联网上升为国家战略，引领建筑产业转型升级，加速全球建筑产业进入工业化、数字化的网络时代。</span></p><p><span data-v-7b8e67be="" class="tipsBoxspan">工业互联网平台成为建筑产业竞争制高点驱动建筑产业&nbsp;Made&nbsp;In&nbsp;Internet，重构绿色智能建筑产业新生态。</span></p><p><span data-v-7b8e67be="" class="tipsBoxspan tipsItem">产品整合产业链，数据整合工艺链，平台颠覆产业世界观，工业互联网平台必将颠覆整个产业的商业模式。</span></p>
     * dissertation_pic : [{"pic_id":"21","pic_url":"https://jt.chinabim.com/static/image//banner2.png"}]
     * dissertationAuthor : [{"author_id":"10","article_id":"87","author_name":"张鸣","author_desc":"博士、民盟成员、高级工程师","author_pic":"https://www.51jiantan.com/static/image/zhangming.png","article_title":"建筑产业工业互联网","publish_time":"2019-09-11"},{"author_id":"16","article_id":"88","author_name":"蒲小强","author_desc":"BIM实战专家","author_pic":"https://www.51jiantan.com/static/image/puxiaoqiang.jpg","article_title":"建筑产品体系","publish_time":"2019-09-11"},{"author_id":"17","article_id":"89","author_name":"杨海潮","author_desc":"政府服务专家","author_pic":"https://www.51jiantan.com/static/image/yanghaichao.jpg","article_title":"数字建筑集成数字城市","publish_time":"2019-09-11"},{"author_id":"18","article_id":"90","author_name":"苏磊","author_desc":"建谊副总裁","author_pic":"https://www.51jiantan.com/static/image/sulei.jpg","article_title":"工业互联网与建筑产业跨界融合，创建建筑新生态","publish_time":"2019-09-11"},{"author_id":"19","article_id":"91","author_name":"龙睿","author_desc":"产品研发专家","author_pic":"https://www.51jiantan.com/static/image/longrui.jpg","article_title":"体验·场景·社群，互联网重塑住宅服务","publish_time":"2019-09-11"},{"author_id":"20","article_id":"92","author_name":"张海玲","author_desc":"成本管理专家","author_pic":"https://www.51jiantan.com/static/image/zhanghailing01.jpg","article_title":"成本控制模型体系","publish_time":"2019-09-11"},{"author_id":"21","article_id":"93","author_name":"赵伟宏","author_desc":"资源管理专家","author_pic":"https://www.51jiantan.com/static/image/zhaoweihong.jpg","article_title":"建筑产业资源低于经济互联网","publish_time":"2019-09-11"},{"author_id":"24","article_id":"94","author_name":"朱晓斌","author_desc":"战略总监","author_pic":"https://www.51jiantan.com/static/image/zhuxiaobin.jpg","article_title":"建筑产业工业互联网走向世界，使边穷地区享有建筑新科技","publish_time":"2019-09-11"},{"author_id":"28","article_id":"751","author_name":"胡育科","author_desc":"中国建筑金属结构协会建筑钢结构分会副会长","author_pic":"https://www.51jiantan.com/static/image/huyuke.png","article_title":"装配式建筑产业平台协同效应","publish_time":"2019-09-11"},{"author_id":"29","article_id":"752","author_name":"邓智铭","author_desc":"阿里云资深产品专家、架构师","author_pic":"https://www.51jiantan.com/static/image/dengzhiming.jpg","article_title":"建筑产业工业互联网平台创新价值","publish_time":"2019-09-11"},{"author_id":"30","article_id":"753","author_name":"林敏","author_desc":"毕埃慕（上海）建筑数据技术股份有限公司创始人、首席顾问","author_pic":"https://www.51jiantan.com/static/image/linmin.png","article_title":"未造先知，匠心协同！基于BIM的BDIP建筑数据集成平台的应用实践","publish_time":"2019-09-11"}]
     */

    private int dissertation_id;
    private String dissertation_title;
    private String dissertation_desc;
    private String type;
    private List<DissertationPicBean> dissertation_pic;
    private List<DissertationAuthorBean> dissertationAuthor;

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

    public String getDissertation_desc() {
        return dissertation_desc;
    }

    public void setDissertation_desc(String dissertation_desc) {
        this.dissertation_desc = dissertation_desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DissertationPicBean> getDissertation_pic() {
        return dissertation_pic;
    }

    public void setDissertation_pic(List<DissertationPicBean> dissertation_pic) {
        this.dissertation_pic = dissertation_pic;
    }

    public List<DissertationAuthorBean> getDissertationAuthor() {
        return dissertationAuthor;
    }

    public void setDissertationAuthor(List<DissertationAuthorBean> dissertationAuthor) {
        this.dissertationAuthor = dissertationAuthor;
    }

    public static class DissertationPicBean {
        /**
         * pic_id : 21
         * pic_url : https://jt.chinabim.com/static/image//banner2.png
         */

        private String pic_id;
        private String pic_url;

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
    }

    public static class DissertationAuthorBean {
        /**
         * author_id : 10
         * article_id : 87
         * author_name : 张鸣
         * author_desc : 博士、民盟成员、高级工程师
         * author_pic : https://www.51jiantan.com/static/image/zhangming.png
         * article_title : 建筑产业工业互联网
         * publish_time : 2019-09-11
         */

        private String author_id;
        private String article_id;
        private String author_name;
        private String author_desc;
        private String author_pic;
        private String article_title;
        private String publish_time;

        public String getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(String author_id) {
            this.author_id = author_id;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_desc() {
            return author_desc;
        }

        public void setAuthor_desc(String author_desc) {
            this.author_desc = author_desc;
        }

        public String getAuthor_pic() {
            return author_pic;
        }

        public void setAuthor_pic(String author_pic) {
            this.author_pic = author_pic;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }
    }
}
