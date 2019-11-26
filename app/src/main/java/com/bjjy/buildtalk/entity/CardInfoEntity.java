package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019-11-14 13:57
 * @project BuildTalk
 * @description:
 */
public class CardInfoEntity implements Serializable {

    /**
     * author_pic : https://www.51jiantan.com/static/image/liuxinke.jpg
     * uploadCardImage : 0
     * author_name : 刘新科
     * cardNumber : null
     * cardValidDate :
     * author_desc : 项目经理
     * author_intro : BIM培训资深讲师
     * attestationStatus : 1
     * is_upload : 1
     */

    private String author_pic;
    private int uploadCardImage;
    private String author_name;
    private String cardNumber;
    private String cardValidDate;
    private String author_desc;
    private String author_intro;
    private int attestationStatus;
    private int is_upload;

    public String getAuthor_pic() {
        return author_pic;
    }

    public void setAuthor_pic(String author_pic) {
        this.author_pic = author_pic;
    }

    public int getUploadCardImage() {
        return uploadCardImage;
    }

    public void setUploadCardImage(int uploadCardImage) {
        this.uploadCardImage = uploadCardImage;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardValidDate() {
        return cardValidDate;
    }

    public void setCardValidDate(String cardValidDate) {
        this.cardValidDate = cardValidDate;
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

    public int getAttestationStatus() {
        return attestationStatus;
    }

    public void setAttestationStatus(int attestationStatus) {
        this.attestationStatus = attestationStatus;
    }

    public int getIs_upload() {
        return is_upload;
    }

    public void setIs_upload(int is_upload) {
        this.is_upload = is_upload;
    }
}
