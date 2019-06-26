package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/21 11:59 AM
 * @project BuildTalk
 * @description:
 */
public class CircleListEntity implements Serializable {

    /**
     * circleInfo : [{"user_id":4,"is_author":0,"name":"♞木有年輪","headImage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132","countAttention":4,"is_attention":0},{"user_id":10,"is_author":1,"name":"张鸣","headImage":"https://www.51jiantan.com/static/image/zhangming.png","countAttention":2,"is_attention":0},{"user_id":3,"is_author":0,"name":"瓦力～杨","headImage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIicn8aEHBJIlBPedmibRnMutRWciaRnlhCicWKewmnk0I59quOicLdaMBLlMcSEqSoibc5VRibW2iaclxfDg/132","countAttention":1,"is_attention":0},{"user_id":47,"is_author":0,"name":"小地瓜","headImage":"https://wx.qlogo.cn/mmopen/vi_32/haxtRyFofTlLtpSdoOWicjs0pNKiavFXHCLY0B8HsZJ6iasThcEtQD9wiazMvBBpQrU4m3Ozx0j6lYjbJTZqUBiaCSA/132","countAttention":1,"is_attention":0},{"user_id":138,"is_author":0,"name":"LIU","headImage":"https://jt.chinabim.com/static/image/156108789119108.png","countAttention":1,"is_attention":0},{"user_id":139,"is_author":0,"name":"建谈新用户  ","headImage":"https://www.51jiantan.com/static/image/1.jpg","countAttention":1,"is_attention":0},{"user_id":145,"is_author":0,"name":"建谈新用户","headImage":"https://www.51jiantan.com/static/image/1.jpg","countAttention":1,"is_attention":2},{"user_id":8,"is_author":0,"name":"一","headImage":"https://wx.qlogo.cn/mmopen/vi_32/tK8jnr2h5VmVkuqUkM5FYu30FBTDaJt7C0aYYev0cEjriaskgFgAt3ryQvtnJ2d0x6SMlXO9DXfGa5uc588SUyg/132","countAttention":0,"is_attention":0},{"user_id":108,"is_author":0,"name":"天的杂货铺","headImage":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJO13zUgGxUnOCNyB26USppIib666sicqwLXpibVKW6YbSwaCxG1rN1gevkFibdExBdWWnA18j4hUMYgg/132","countAttention":0,"is_attention":0}]
     * page : 1
     * page_count : 1
     */

    private String page;
    private int page_count;
    private List<CircleInfoBean> circleInfo;

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

    public List<CircleInfoBean> getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(List<CircleInfoBean> circleInfo) {
        this.circleInfo = circleInfo;
    }

    public static class CircleInfoBean {
        /**
         * user_id : 4
         * is_author : 0
         * name : ♞木有年輪
         * headImage : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132
         * countAttention : 4
         * is_attention : 0
         */

        private int user_id;
        private int is_author;
        private String name;
        private String headImage;
        private int countAttention;
        private int is_attention;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getCountAttention() {
            return countAttention;
        }

        public void setCountAttention(int countAttention) {
            this.countAttention = countAttention;
        }

        public int getIs_attention() {
            return is_attention;
        }

        public void setIs_attention(int is_attention) {
            this.is_attention = is_attention;
        }
    }
}
