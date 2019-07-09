package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/5/9 10:27 AM
 * @project BuildTalk
 * @description:
 */
public class SaveRecordEntity implements Serializable {


    /**
     * guestbookInfo : [{"guestbook_id":286,"user_id":139,"content":"哈哈","guestbook_time":"2019-07-08 18:16:50","name":"Power","headImage":"https://jt.chinabim.com/static/image/156151706066913.jpg","countpraise":0,"isPraise":0,"isDelete":1},{"guestbook_id":284,"user_id":158,"content":"来咯哦哦哦。界面我的","guestbook_time":"2019-07-08 16:44:44","name":"正确认识自己","headImage":"https://jt.chinabim.com/static/image/156256480005055.jpg","countpraise":4,"isPraise":0,"isDelete":0},{"guestbook_id":283,"user_id":48,"content":"哥哥哥哥哥哥vgggggggggggffvcxzzsdfggbbbbgggvvvvvcv","guestbook_time":"2019-07-08 15:46:10","name":"周一","headImage":"https://jt.chinabim.com/static/image/156257479061413.png","countpraise":2,"isPraise":0,"isDelete":0},{"guestbook_id":282,"user_id":108,"content":"你","guestbook_time":"2019-07-08 15:09:31","name":"天","headImage":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJO13zUgGxUnOCNyB26USppIib666sicqwLXpibVKW6YbSwaCxG1rN1gevkFibdExBdWWnA18j4hUMYgg/132","countpraise":2,"isPraise":0,"isDelete":0},{"guestbook_id":253,"user_id":165,"content":"你","guestbook_time":"2019-07-01 14:58:06","name":"哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈哈","headImage":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIrS59fpvXfuc6fqqeOWEE2Qmwoc3JNGPR29Ij6SUDzqANoZccUyuWDhwP7w7uibGS5Wj9VQ1raGsA/132","countpraise":2,"isPraise":0,"isDelete":0},{"guestbook_id":236,"user_id":138,"content":"2","guestbook_time":"2019-06-24 11:15:01","name":"121","headImage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIxrv7XYG5K0wiadh3vRdKwvQAssiceDsn2xXZ5vOF0W8BMcGZWcZM6zFqib08Fiazicabhx2qtk0EmOlw/132","countpraise":2,"isPraise":0,"isDelete":0},{"guestbook_id":235,"user_id":138,"content":"1","guestbook_time":"2019-06-24 11:14:56","name":"121","headImage":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIxrv7XYG5K0wiadh3vRdKwvQAssiceDsn2xXZ5vOF0W8BMcGZWcZM6zFqib08Fiazicabhx2qtk0EmOlw/132","countpraise":3,"isPraise":0,"isDelete":0}]
     * countGuestbookNum : 7
     * page : 1
     * page_count : 1
     */

    private int countGuestbookNum;
    private int page;
    private int page_count;
    private List<GuestbookInfoBean> guestbookInfo;

    public int getCountGuestbookNum() {
        return countGuestbookNum;
    }

    public void setCountGuestbookNum(int countGuestbookNum) {
        this.countGuestbookNum = countGuestbookNum;
    }

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

    public List<GuestbookInfoBean> getGuestbookInfo() {
        return guestbookInfo;
    }

    public void setGuestbookInfo(List<GuestbookInfoBean> guestbookInfo) {
        this.guestbookInfo = guestbookInfo;
    }

    public static class GuestbookInfoBean {
        /**
         * guestbook_id : 286
         * user_id : 139
         * content : 哈哈
         * guestbook_time : 2019-07-08 18:16:50
         * name : Power
         * headImage : https://jt.chinabim.com/static/image/156151706066913.jpg
         * countpraise : 0
         * isPraise : 0
         * isDelete : 1
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

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }
    }
}
