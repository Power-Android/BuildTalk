package com.bjjy.buildtalk.app;

import com.bjjy.buildtalk.core.db.DbHelper;
import com.bjjy.buildtalk.core.http.helper.HttpHelper;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.preference.PreferenceHelper;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
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

    @Override
    public Observable<BaseResponse<List<BannerEntity>>> discoverBanner(Map<String, String> headers, Map<String, String> paramas) {
        return mHttpHelper.discoverBanner(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(Map<String, String> headers, Map<String, String> paramas) {
        return mHttpHelper.everyDayTalk(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<CourseEntity>> courseInfo(Map<String, String> headers, Map<String, String> paramas) {
        return mHttpHelper.courseInfo(headers,paramas);
    }

    @Override
    public Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(Map<String, String> headers, Map<String, String> paramas) {
        return mHttpHelper.everyDayTalkList(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.everyTalkDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.saveRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<TestEntity>> praiseRecord(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.praiseRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<TestEntity>> collectArticle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.collectArticle(headers, params);
    }
}
