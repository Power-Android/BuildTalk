package com.bjjy.buildtalk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/5/15 11:28 AM
 * @project BuildTalk
 * @description:
 */
public class CircleEntity implements Serializable, MultiItemEntity {
    private int itemType;
    private String data;

    public CircleEntity(int itemType) {
        this.itemType = itemType;
    }

    public CircleEntity(String data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
