package com.bjjy.buildtalk.app;

import com.bjjy.buildtalk.core.db.DbHelper;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.http.helper.HttpHelper;
import com.bjjy.buildtalk.core.preference.PreferenceHelper;
import com.bjjy.buildtalk.entity.TestEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description: 数据统一管理类
 */
public class DataManager implements HttpHelper, DbHelper, PreferenceHelper {
    private HttpHelper mHttpHelper;
    private DbHelper mDbHelper;
    private PreferenceHelper mPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferenceHelper = preferenceHelper;
    }

    @Override
    public void setLoginStatus(boolean isLogin) {

    }

    @Override
    public boolean getLoginStatus() {
        return false;
    }

    @Override
    public void setLoginAccount(String account) {

    }

    @Override
    public String getLoginAccount() {
        return null;
    }

    @Override
    public void setIsGuide(boolean isGuide) {
        mPreferenceHelper.setIsGuide(isGuide);
    }

    @Override
    public boolean getIsGuide() {
        return mPreferenceHelper.getIsGuide();
    }

    @Override
    public Observable<BaseResponse<List<TestEntity>>> signTest(Map<String, String> headers, Map<String,String> paramas) {
        return mHttpHelper.signTest(headers,paramas);
    }
}
