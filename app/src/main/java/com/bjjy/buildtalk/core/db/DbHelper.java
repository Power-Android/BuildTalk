package com.bjjy.buildtalk.core.db;

import com.bjjy.buildtalk.core.greendao.HistoryData;

import java.util.List;

/**
 * @author power
 * @date 2019/4/25 11:42 AM
 * @project BuildTalk
 * @description: 数据库
 */
public interface DbHelper {
    /**
     * Add search history data
     *
     * @param data  added string
     * @return  List<HistoryData>
     */
    List<HistoryData> addHistoryData(String data);

    /**
     * Clear all search history data
     */
    void clearAllHistoryData();

    /**
     * Clear all search history data
     */
    void deleteHistoryDataById(Long id);

    /**
     * Load all history data
     *
     * @return List<HistoryData>
     */
    List<HistoryData> loadAllHistoryData();
}
