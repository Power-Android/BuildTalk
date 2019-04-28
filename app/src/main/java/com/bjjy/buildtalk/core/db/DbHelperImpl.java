package com.bjjy.buildtalk.core.db;

import android.database.sqlite.SQLiteDatabase;

import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.greendao.DaoMaster;
import com.bjjy.buildtalk.core.greendao.DaoSession;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/25 11:48 AM
 * @project BuildTalk
 * @description:
 */
public class DbHelperImpl implements DbHelper {

    private DaoSession daoSession;

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
}
