package com.bjjy.buildtalk.core.db;

import android.database.sqlite.SQLiteDatabase;

import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.greendao.CircleHistoryDataDao;
import com.bjjy.buildtalk.core.greendao.DaoMaster;
import com.bjjy.buildtalk.core.greendao.DaoSession;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.core.greendao.HistoryDataDao;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/25 11:48 AM
 * @project BuildTalk
 * @description:
 */
public class DbHelperImpl implements DbHelper {

    private static final int HISTORY_LIST_SIZE = 20;

    private DaoSession daoSession;

    private String data;

    private List<HistoryData> historyDataList;
    private HistoryData historyData;

    private List<CircleHistoryData> circleHistoryDataList;
    private CircleHistoryData circleHistoryData;

    @Inject
    DbHelperImpl() {
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper =
                new DaoMaster.DevOpenHelper(App.getContext(), Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        this.data = data;
        getHistoryDataList();
        createHistoryData();
        if (historyDataForward()) {
            return historyDataList;
        }

        if (historyDataList.size() < HISTORY_LIST_SIZE) {
            getHistoryDataDao().insert(historyData);
        } else {
            historyDataList.remove(0);
            historyDataList.add(historyData);
            getHistoryDataDao().deleteAll();
            getHistoryDataDao().insertInTx(historyDataList);
        }
        return historyDataList;
    }

    @Override
    public void clearAllHistoryData() {
        daoSession.getHistoryDataDao().deleteAll();
    }

    @Override
    public void deleteHistoryDataById(Long id) {
        daoSession.getHistoryDataDao().deleteByKey(id);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return daoSession.getHistoryDataDao().loadAll();
    }

    /**
     * 历史数据前移
     *
     * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
     */
    private boolean historyDataForward() {
        //重复搜索时进行历史记录前移
        Iterator<HistoryData> iterator = historyDataList.iterator();
        //不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
        while (iterator.hasNext()) {
            HistoryData historyData1 = iterator.next();
            if (historyData1.getData().equals(data)) {
                historyDataList.remove(historyData1);
                historyDataList.add(historyData);
                getHistoryDataDao().deleteAll();
                getHistoryDataDao().insertInTx(historyDataList);
                return true;
            }
        }
        return false;
    }

    private void getHistoryDataList() {
        historyDataList = getHistoryDataDao().loadAll();
    }

    private void createHistoryData() {
        historyData = new HistoryData();
        historyData.setDate(System.currentTimeMillis());
        historyData.setData(data);
    }

    private HistoryDataDao getHistoryDataDao() {
        return daoSession.getHistoryDataDao();
    }

    @Override
    public List<CircleHistoryData> addCircleHistoryData(String data) {
        this.data = data;
        getCircleHistoryDataList();
        createCircleHistoryData();
        if (circleHistoryDataForward()) {
            return circleHistoryDataList;
        }

        if (circleHistoryDataList.size() < HISTORY_LIST_SIZE) {
            getCircleHistoryDataDao().insert(circleHistoryData);
        } else {
            circleHistoryDataList.remove(0);
            circleHistoryDataList.add(circleHistoryData);
            getCircleHistoryDataDao().deleteAll();
            getCircleHistoryDataDao().insertInTx(circleHistoryDataList);
        }
        return circleHistoryDataList;
    }

    @Override
    public void clearAllCircleHistoryData() {
        daoSession.getCircleHistoryDataDao().deleteAll();
    }

    @Override
    public void deleteCircleHistoryDataById(Long id) {
        daoSession.getCircleHistoryDataDao().deleteByKey(id);
    }

    @Override
    public List<CircleHistoryData> loadAllCircleHistoryData() {
        return daoSession.getCircleHistoryDataDao().loadAll();
    }

    /**
     * 历史数据前移
     *
     * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
     */
    private boolean circleHistoryDataForward() {
        //重复搜索时进行历史记录前移
        Iterator<CircleHistoryData> iterator = circleHistoryDataList.iterator();
        //不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
        while (iterator.hasNext()) {
            CircleHistoryData circleHistoryData1 = iterator.next();
            if (circleHistoryData1.getData().equals(data)) {
                circleHistoryDataList.remove(circleHistoryData1);
                circleHistoryDataList.add(circleHistoryData);
                getCircleHistoryDataDao().deleteAll();
                getCircleHistoryDataDao().insertInTx(circleHistoryDataList);
                return true;
            }
        }
        return false;
    }

    private void getCircleHistoryDataList() {
        circleHistoryDataList = getCircleHistoryDataDao().loadAll();
    }

    private void createCircleHistoryData() {
        circleHistoryData = new CircleHistoryData();
        circleHistoryData.setDate(System.currentTimeMillis());
        circleHistoryData.setData(data);
    }

    private CircleHistoryDataDao getCircleHistoryDataDao() {
        return daoSession.getCircleHistoryDataDao();
    }
}
