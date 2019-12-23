package com.bjjy.buildtalk.core.db;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.greendao.CircleHistoryDataDao;
import com.bjjy.buildtalk.core.greendao.DaoMaster;
import com.bjjy.buildtalk.core.greendao.DaoSession;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.core.greendao.HistoryDataDao;
import com.bjjy.buildtalk.core.greendao.SongsEntityDao;
import com.bjjy.buildtalk.core.greendao.UserDao;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.utils.LogUtils;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    private List<User> mUserList;
    private List<SongsEntity> mSongsList;

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

    public DaoSession getGreenDao() {
        if (daoSession != null)
            return daoSession;
        return null;
    }

    private UserDao getUserDao() {
        return daoSession.getUserDao();
    }

    private SongsEntityDao getSongsDao() {
        return daoSession.getSongsEntityDao();
    }


    @Override
    public void addSongsData(List<SongsEntity> songs) {
        if (getSongsDao() != null) {
            getSongsDao().deleteAll();
        }
        getSongsDao().insertInTx(songs);
    }

    @Override
    public void clearAllSongsData() {
        getSongsDao().deleteAll();
    }

    @Override
    public List<SongsEntity> getSongsData() {
        return getSongsDao().loadAll();
    }

    @SuppressLint("CheckResult")
    @Override
    public SongsEntity querySongsDataById(String songId) {
//        List<SongsEntity> list = daoSession.queryRaw(SongsEntity.class, "where article_id = ?", songId);
        mSongsList = getSongsDao().queryBuilder().where(SongsEntityDao.Properties.Article_id.eq(songId)).list();
        if (mSongsList.size() > 0) {
            return mSongsList.get(0);
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        if (getUserDao() != null) {
            getUserDao().deleteAll();
        }
        getUserDao().insert(user);
    }

    @Override
    public User getUser() {
        mUserList = getUserDao().loadAll();
        if (mUserList.size() == 0) {
            return new User();
        }
        return mUserList.get(0);
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        if (isLogin) {
            getUser().setLoginStatus(isLogin);
            getUserDao().update(getUser());
        } else {
            getUserDao().deleteAll();
        }
    }

    @Override
    public boolean getLoginStatus() {
        return getUser().isLoginStatus();
    }

    @Override
    public void loginOut() {
        getUserDao().deleteAll();
    }

    @Override
    public void setVerifyRecordCount(int count) {
        getUser().setCountCheckRecord(count);
    }

    @Override
    public int getVerifyRecordCount() {
        return getUser().getCountCheckRecord();
    }

    @Override
    public void setUserType(String user_type) {
        getUser().setUser_type(user_type);
    }

    @Override
    public String getUserType() {
        return null;
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
