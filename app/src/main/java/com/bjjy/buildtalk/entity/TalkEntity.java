package com.bjjy.buildtalk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author power
 * @date 2019/4/30 11:58 AM
 * @project BuildTalk
 * @description:
 */
public class TalkEntity implements Serializable, MultiItemEntity {
    private int itemType;

    public TalkEntity(int itemType) {
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
