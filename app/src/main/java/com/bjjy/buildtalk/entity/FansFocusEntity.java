package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/23 11:20 AM
 * @project BuildTalk
 * @description:
 */
public class FansFocusEntity implements Serializable {

    /**
     * page : 1
     * page_count : 1
     * myFansInfo : [{"attention_id":4,"user_id":4,"attention_user":10,"source":null,"name":"♞木有年輪","headImage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132","countAttention":4,"is_attention":0},{"attention_id":21,"user_id":108,"attention_user":10,"source":"xcx","name":"天的杂货铺","headImage":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJO13zUgGxUnOCNyB26USppIib666sicqwLXpibVKW6YbSwaCxG1rN1gevkFibdExBdWWnA18j4hUMYgg/132","countAttention":0,"is_attention":0}]
     */

    private String page;
    private int page_count;
    private List<MyFansInfoBean> myFansInfo;
    private List<AttentionInfoBean> attentionInfo;
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

    public List<MyFansInfoBean> getMyFansInfo() {
        return myFansInfo;
    }

    public void setMyFansInfo(List<MyFansInfoBean> myFansInfo) {
        this.myFansInfo = myFansInfo;
    }

    public List<AttentionInfoBean> getAttentionInfo() {
        return attentionInfo;
    }

    public void setAttentionInfo(List<AttentionInfoBean> attentionInfo) {
        this.attentionInfo = attentionInfo;
    }

    public static class MyFansInfoBean {
        /**
         * attention_id : 4
         * user_id : 4
         * attention_user : 10
         * source : null
         * name : ♞木有年輪
         * headImage : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRFIZAykeug0nCgYFIb6A0G67pNBVviawlFr1icWDC3SuUIq4ng6tlrveag0iaicwxnzCZF52o5UVDBg/132
         * countAttention : 4
         * is_attention : 0
         */

        private int attention_id;
        private int user_id;
        private int attention_user;
        private Object source;
        private String name;
        private String headImage;
        private int countAttention;
        private int is_attention;
        private int is_author;

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }

        public int getAttention_id() {
            return attention_id;
        }

        public void setAttention_id(int attention_id) {
            this.attention_id = attention_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getAttention_user() {
            return attention_user;
        }

        public void setAttention_user(int attention_user) {
            this.attention_user = attention_user;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
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

    public static class AttentionInfoBean {
        /**
         * attention_id : 2
         * user_id : 10
         * attention_user : 2
         * source : null
         * name : 扇泥贝尔
         * headImage : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erBx7bibQIQS9FicSolWglXHHxcfNfTIIUQGvuHiaogIIv5bfqTrgfia8KpBqm4Il1XObBKtmvw6jXFAw/132
         * countAttention : 1
         * is_attention : 0
         */

        private int attention_id;
        private int user_id;
        private int attention_user;
        private Object source;
        private String name;
        private String headImage;
        private int countAttention;
        private int is_attention;
        private int is_author;

        public int getIs_author() {
            return is_author;
        }

        public void setIs_author(int is_author) {
            this.is_author = is_author;
        }

        public int getAttention_id() {
            return attention_id;
        }

        public void setAttention_id(int attention_id) {
            this.attention_id = attention_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getAttention_user() {
            return attention_user;
        }

        public void setAttention_user(int attention_user) {
            this.attention_user = attention_user;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
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
