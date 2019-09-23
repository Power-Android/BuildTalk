package com.bjjy.buildtalk.app;

import com.bjjy.buildtalk.core.db.DbHelper;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.core.http.helper.HttpHelper;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.preference.PreferenceHelper;
import com.bjjy.buildtalk.entity.ActivityEntity;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CircleListEntity;
import com.bjjy.buildtalk.entity.CircleMasterEntity;
import com.bjjy.buildtalk.entity.CircleMasterInfoEntity;
import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.CollectEntity;
import com.bjjy.buildtalk.entity.CommentSuccessEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;
import com.bjjy.buildtalk.entity.DissertationEntity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.IndustryMasterEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.bjjy.buildtalk.entity.MyCardEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

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
    public void loginOut() {
        mDbHelper.loginOut();
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
    public Observable<BaseResponse<SaveRecordEntity>> saveRecord(Map<String, String> headers, Map<String, String> params) {
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
    public Observable<BaseResponse<String>> createCircle(Map<String, String> headers, Map<String, String> params) {
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

    @Override
    public Observable<BaseResponse<CommentSuccessEntity>> publishComment(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.publishComment(headers, params);
    }

    @Override
    public Observable<BaseResponse<PraiseEntity>> themeParise(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.themeParise(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> publishTheme(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.publishTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updateTheme(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.updateTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> collectTheme(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.collectTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> deleteTheme(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.deleteTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleMasterInfoEntity>> circleMasterInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.circleMasterInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> quitCircle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.quitCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<MyCardEntity>> myCard(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.myCard(headers, params);
    }

    @Override
    public Observable<BaseResponse<MemberEntity>> circleUser(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.circleUser(headers, params);
    }


    @Override
    public Observable<BaseResponse<SearchCircleInfoEntity>> searchCircleInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchCircleInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> searchThemeDetail(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchThemeDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> commentPageHandle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.commentPageHandle(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> deleteGuestbook(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.deleteGuestbook(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchResultEntity>> searchTalkHistory(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchTalkHistory(headers, params);
    }

    @Override
    public Observable<BaseResponse<IndustryMasterEntity>> searchIndustryMaster(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchIndustryMaster(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<CircleMasterEntity>>> searchCircleMaster(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchCircleMaster(headers, params);
    }

    @Override
    public Observable<BaseResponse<MasterListEntity>> searchAuthor(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchAuthor(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleListEntity>> searchCircleList(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchCircleList(headers, params);
    }

    @Override
    public Observable<BaseResponse<MasterDetailEntity>> userDetail(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.userDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<FansFocusEntity>> myFans(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.myFans(headers, params);
    }

    @Override
    public Observable<BaseResponse<FansFocusEntity>> myAttention(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.myAttention(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchResultEntity>> searchUserCircle(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchUserCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<CollectEntity>> myCollect(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.myCollect(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> attention(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.attention(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> userInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.userInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updateUserInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.updateUserInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updatePhone(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.updatePhone(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> questionFeedback(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.questionFeedback(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<AleadyBuyEntity>>> alreadyBuy(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.alreadyBuy(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> checkisBind(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.checkisBind(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> checkMobileCodeByAPP(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.checkMobileCodeByAPP(headers, params);
    }

    @Override
    public Observable<BaseResponse<CourseListEntity>> getCourseList(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.getCourseList(headers, params);
    }

    @Override
    public Observable<BaseResponse<PayOrderEntity>> payOrder(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.payOrder(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> updateCircleInfo(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.updateCircleInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<VersionRecordEntity>>> versionRecord() {
        return mHttpHelper.versionRecord();
    }

    @Override
    public Observable<BaseResponse<ActivityEntity>> getActivity() {
        return mHttpHelper.getActivity();
    }

    @Override
    public Observable<BaseResponse<IEntity>> addChoiceness(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.addChoiceness(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> getThumb(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.getThumb(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> userShieldRecord(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.userShieldRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<DissertationEntity>>> searchDissertation() {
        return mHttpHelper.searchDissertation();
    }

    @Override
    public Observable<BaseResponse<DissertationDetailEntity>> searchDissertationDetail(Map<String, String> headers, Map<String, String> params) {
        return mHttpHelper.searchDissertationDetail(headers, params);
    }
}
