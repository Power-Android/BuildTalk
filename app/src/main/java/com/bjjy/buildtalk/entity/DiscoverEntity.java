package com.bjjy.buildtalk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/4/28 10:17 AM
 * @project BuildTalk
 * @description: 首页发现实体类
 */
public class DiscoverEntity implements Serializable, MultiItemEntity {

    private int itemType;

    public DiscoverEntity(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
