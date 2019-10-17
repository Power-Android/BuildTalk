package com.bjjy.buildtalk.entity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/30 10:14 AM
 * @project BuildTalk
 * @description:
 */
public class GuestBookEntity {
    /**
     * guestbookInfo : [{"guestbook_id":190,"user_id":47,"content":"1","guestbook_time":"2019-05-10 17:58:35","nickname":"小地瓜","headimage":"https://wx.qlogo.cn/mmopen/vi_32/haxtRyFofTlLtpSdoOWicjs0pNKiavFXHCLY0B8HsZJ6iasThcEtQD9wiazMvBBpQrU4m3Ozx0j6lYjbJTZqUBiaCSA/132","countpraise":0,"isPraise":0},{"guestbook_id":189,"user_id":47,"content":"1","guestbook_time":"2019-05-10 17:41:57","nickname":"小地瓜","headimage":"https://wx.qlogo.cn/mmopen/vi_32/haxtRyFofTlLtpSdoOWicjs0pNKiavFXHCLY0B8HsZJ6iasThcEtQD9wiazMvBBpQrU4m3Ozx0j6lYjbJTZqUBiaCSA/132","countpraise":0,"isPraise":0}]
     * page : 1
     * page_count : 7
     */

    private int page;
    private int page_count;
    private String countGuestbookNum;
    private List<GuestbookInfoBean> guestbookInfo;

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

    public String getCountGuestbookNum() {
        return countGuestbookNum;
    }

    public void setCountGuestbookNum(String countGuestbookNum) {
        this.countGuestbookNum = countGuestbookNum;
    }

    public List<GuestbookInfoBean> getGuestbookInfo() {
        return guestbookInfo;
    }

    public void setGuestbookInfo(List<GuestbookInfoBean> guestbookInfo) {
        this.guestbookInfo = guestbookInfo;
    }

    public static class GuestbookInfoBean {
        /**
         * guestbook_id : 190
         * user_id : 47
         * content : 1
         * guestbook_time : 2019-05-10 17:58:35
         * nickname : 小地瓜
         * headimage : https://wx.qlogo.cn/mmopen/vi_32/haxtRyFofTlLtpSdoOWicjs0pNKiavFXHCLY0B8HsZJ6iasThcEtQD9wiazMvBBpQrU4m3Ozx0j6lYjbJTZqUBiaCSA/132
         * countpraise : 0
         * isPraise : 0
         */

        private int guestbook_id;
        private int user_id;
        private String content;
        private String guestbook_time;
        private String name;
        private String headImage;
        private int countpraise;
        private int isPraise;
        private int isDelete;

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

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

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

        public String getGuestbook_time() {
            return guestbook_time;
        }

        public void setGuestbook_time(String guestbook_time) {
            this.guestbook_time = guestbook_time;
        }

        public String getNickname() {
            return name;
        }

        public void setNickname(String nickname) {
            this.name = nickname;
        }

        public String getHeadimage() {
            return headImage;
        }

        public void setHeadimage(String headimage) {
            this.headImage = headimage;
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
