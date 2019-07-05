package com.bjjy.buildtalk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/7/4 3:48 PM
 * @project BuildTalk
 * @description:
 */
public class PayOrderEntity implements Serializable {

    /**
     * appid : wx24a51a57c203d22a
     * partnerid : 1522437631
     * prepayid : wx041542238816930157c98c921517377600
     * noncestr : qomuNSWHVMYf9YgI
     * timestamp : 1562226114
     * package : Sign=WXPay
     * sign : C3763887545E87C87336ABC1E67CE28A
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
