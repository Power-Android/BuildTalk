package com.bjjy.buildtalk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019-09-03 11:12
 * @project BuildTalk
 * @description:
 */
public class VersionRecordEntity implements Serializable, MultiItemEntity {

    private int itemType;
    private boolean isExpand;

    public VersionRecordEntity(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    /**
     * record_id : 1
     * update_desc : ["优化了关注、圈主解散圈子、播放当前状态的显示问题","统一名词显示","新增话题圈子搜索","主题加精","每日一谈列表排序","建谈版本迭代记录功能"]
     * update_version : V2.01
     * update_time : 1567404917
     */

    private int record_id;
    private String update_version;
    private int update_time;
    private List<String> update_desc;

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getUpdate_version() {
        return update_version;
    }

    public void setUpdate_version(String update_version) {
        this.update_version = update_version;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public List<String> getUpdate_desc() {
        return update_desc;
    }

    public void setUpdate_desc(List<String> update_desc) {
        this.update_desc = update_desc;
    }
}
