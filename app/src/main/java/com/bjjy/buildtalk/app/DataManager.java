package com.bjjy.buildtalk.app;

import com.bjjy.buildtalk.core.db.DbHelper;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.core.http.helper.HttpHelper;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.preference.PreferenceHelper;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


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
    //==========================================>PreferenceHelper<==========================================================

    @Override
    public void setIsGuide(boolean isGuide) {
        mPreferenceHelper.setIsGuide(isGuide);
    }

    @Override
    public boolean getIsGuide() {
        return mPreferenceHelper.getIsGuide();
    }

    //==========================================>DbHelper<==================================================================


    @Override
    public void addUser(User user) {
        mDbHelper.addUser(user);
    }

    @Override
    public User getUser() {
        return mDbHelper.getUser();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mDbHelper.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return mDbHelper.getLoginStatus();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        return mDbHelper.addHistoryData(data);
    }

    @Override
    public void clearAllHistoryData() {
        mDbHelper.clearAllHistoryData();
    }

    @Override
    public void deleteHistoryDataById(Long id) {
        mDbHelper.deleteHistoryDataById(id);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDbHelper.loadAllHistoryData();
    }

    @Override
    public List<CircleHistoryData> addCircleHistoryData(String data) {
        return mDbHelper.addCircleHistoryData(data);
    }

    @Override
    public void clearAllCircleHistoryData() {
        mDbHelper.clearAllCircleHistoryData();
    }

    @Override
    public void deleteCircleHistoryDataById(Long id) {
        mDbHelper.deleteCircleHistoryDataById(id);
    }

    @Override
    public List<CircleHistoryData> loadAllCircleHistoryData() {
        return mDbHelper.loadAllCircleHistoryData();
    }

    //==========================================>HttpHelper<==================================================================

    @Override
    public Observable<BaseResponse<List<IEntity>>> signTest(Map<String, String> headers, Map<String,String> paramas) {
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
    public Observable<BaseResponse<GuestBookEntity>> guestBookList(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.guestBookList(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.saveRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> praiseRecord(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.praiseRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> collectArticle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.collectArticle(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> sendSms(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.sendSms(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> mobileRegister(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.mobileRegister(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleEntity>> myCircle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.myCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> uploadFiles(MultipartBody.Part file) {
        return mHttpHelper.uploadFiles(file);
    }

    @Override
    public Observable<BaseResponse<List<CircleTagEntity>>> searchCircleTags(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchCircleTags(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> createCircle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.createCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchResultEntity>> searchHistory(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchHistory(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleInfoEntity>> circleInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.circleInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity>> themeInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.themeInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> uploadFiles(List<MultipartBody.Part> files) {
        return mHttpHelper.uploadFiles(files);
    }

    @Override
    public Observable<BaseResponse<IEntity>> joinCircle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.joinCircle(headers, params);
    }
}
