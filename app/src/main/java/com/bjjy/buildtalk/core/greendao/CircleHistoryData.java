package com.bjjy.buildtalk.core.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author power
 * @date 2019/5/15 9:54 AM
 * @project BuildTalk
 * @description:
 */
@Entity
public class CircleHistoryData {

    @Id(autoincrement = true)
    private Long id;

    private long date;

    private String data;

    @Generated(hash = 1715562558)
    public CircleHistoryData(Long id, long date, String data) {
        this.id = id;
        this.date = date;
        this.data = data;
    }

    @Generated(hash = 1027926776)
    public CircleHistoryData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
