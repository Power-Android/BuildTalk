package com.bjjy.buildtalk.core.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.entity.SongsEntity;

import com.bjjy.buildtalk.core.greendao.CircleHistoryDataDao;
import com.bjjy.buildtalk.core.greendao.HistoryDataDao;
import com.bjjy.buildtalk.core.greendao.UserDao;
import com.bjjy.buildtalk.core.greendao.SongsEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig circleHistoryDataDaoConfig;
    private final DaoConfig historyDataDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig songsEntityDaoConfig;

    private final CircleHistoryDataDao circleHistoryDataDao;
    private final HistoryDataDao historyDataDao;
    private final UserDao userDao;
    private final SongsEntityDao songsEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        circleHistoryDataDaoConfig = daoConfigMap.get(CircleHistoryDataDao.class).clone();
        circleHistoryDataDaoConfig.initIdentityScope(type);

        historyDataDaoConfig = daoConfigMap.get(HistoryDataDao.class).clone();
        historyDataDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        songsEntityDaoConfig = daoConfigMap.get(SongsEntityDao.class).clone();
        songsEntityDaoConfig.initIdentityScope(type);

        circleHistoryDataDao = new CircleHistoryDataDao(circleHistoryDataDaoConfig, this);
        historyDataDao = new HistoryDataDao(historyDataDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        songsEntityDao = new SongsEntityDao(songsEntityDaoConfig, this);

        registerDao(CircleHistoryData.class, circleHistoryDataDao);
        registerDao(HistoryData.class, historyDataDao);
        registerDao(User.class, userDao);
        registerDao(SongsEntity.class, songsEntityDao);
    }
    
    public void clear() {
        circleHistoryDataDaoConfig.clearIdentityScope();
        historyDataDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
        songsEntityDaoConfig.clearIdentityScope();
    }

    public CircleHistoryDataDao getCircleHistoryDataDao() {
        return circleHistoryDataDao;
    }

    public HistoryDataDao getHistoryDataDao() {
        return historyDataDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SongsEntityDao getSongsEntityDao() {
        return songsEntityDao;
    }

}
