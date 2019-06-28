package com.bjjy.buildtalk.entity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/6/27 4:52 PM
 * @project BuildTalk
 * @description:
 */
public class AleadyBuyEntity implements Serializable {

    /**
     * order_id : 2019021681682
     * order_time : 2019-02-16 16:57:22
     * order_price : 0.01
     * is_pay : 0
     * order_name : ArchiCAD课程
     * type : 2
     * data_id : 9
     * circle_id : 2
     */

    private String order_id;
    private String order_time;
    private String order_price;
    private int is_pay;
    private String order_name;
    private int type;
    private int data_id;
    private String circle_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }
}
