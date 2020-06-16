

package com.bjjy.buildtalk.core.http.helper;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.http.api.ApiService;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.ActivityEntity;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CardInfoEntity;
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
import com.bjjy.buildtalk.entity.DisrOrAttenEntity;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;
import com.bjjy.buildtalk.entity.DissertationEntity;
import com.bjjy.buildtalk.entity.DissertationListEntity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.IndustryMasterEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.bjjy.buildtalk.entity.MyCardEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public class HttpHelperImpl implements HttpHelper {

    private ApiService mApiService;

    @Inject
    HttpHelperImpl(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseResponse<List<IEntity>>> signTest(Map<String, String> headers, Map<String, String> paramas) {
        return mApiService.signTest(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<List<BannerEntity>>> discoverBanner(Map<String, String> headers, Map<String, String> paramas) {
        return mApiService.discoverBanner(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(Map<String, String> headers, Map<String, String> paramas) {
        return mApiService.everyDayTalk(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<CourseEntity>> courseInfo(Map<String, String> headers, Map<String, String> paramas) {
        return mApiService.courseInfo(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(Map<String, String> headers, Map<String, String> paramas) {
        return mApiService.everyDayTalkList(headers, paramas);
    }

    @Override
    public Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(Map<String, String> headers, Map<String, String> params) {
        return mApiService.everyTalkDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<GuestBookEntity>> guestBookList(Map<String, String> headers, Map<String, String> params) {
        return mApiService.guestBookList(headers, params);
    }

    @Override
    public Observable<BaseResponse<SaveRecordEntity>> saveRecord(Map<String, String> headers, Map<String, String> params) {
        return mApiService.saveRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> praiseRecord(Map<String, String> headers, Map<String, String> params) {
        return mApiService.praiseRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> collectArticle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.collectArticle(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> sendSms(Map<String, String> headers, Map<String, String> params) {
        return mApiService.sendSms(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> mobileRegister(Map<String, String> headers, Map<String, String> params) {
        return mApiService.mobileRegister(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleEntity>> myCircle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.myCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> uploadFiles(MultipartBody.Part file) {
        return mApiService.uploadFiles(file);
    }

    @Override
    public Observable<BaseResponse<List<CircleTagEntity>>> searchCircleTags(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchCircleTags(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> createCircle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.createCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchResultEntity>> searchHistory(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchHistory(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleInfoEntity>> circleInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.circleInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity>> themeInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.themeInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> uploadFiles(List<MultipartBody.Part> files) {
        return mApiService.uploadFiles(files);
    }

    @Override
    public Observable<BaseResponse<IEntity>> joinCircle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.joinCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<CommentSuccessEntity>> publishComment(Map<String, String> headers, Map<String, String> params) {
        return mApiService.publishComment(headers, params);
    }

    @Override
    public Observable<BaseResponse<PraiseEntity>> themeParise(Map<String, String> headers, Map<String, String> params) {
        return mApiService.themeParise(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> publishTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.publishTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updateTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.updateTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> collectTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.collectTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> deleteTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.deleteTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleMasterInfoEntity>> circleMasterInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.circleMasterInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> quitCircle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.quitCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<MyCardEntity>> myCard(Map<String, String> headers, Map<String, String> params) {
        return mApiService.myCard(headers, params);
    }

    @Override
    public Observable<BaseResponse<MemberEntity>> circleUser(Map<String, String> headers, Map<String, String> params) {
        return mApiService.circleUser(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchCircleInfoEntity>> searchCircleInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchCircleInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> searchThemeDetail(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchThemeDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> commentPageHandle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.commentPageHandle(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> deleteGuestbook(Map<String, String> headers, Map<String, String> params) {
        return mApiService.deleteGuestbook(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchResultEntity>> searchTalkHistory(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchTalkHistory(headers, params);
    }

    @Override
    public Observable<BaseResponse<IndustryMasterEntity>> searchIndustryMaster(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchIndustryMaster(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<CircleMasterEntity>>> searchCircleMaster(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchCircleMaster(headers, params);
    }

    @Override
    public Observable<BaseResponse<MasterListEntity>> searchAuthor(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchAuthor(headers, params);
    }

    @Override
    public Observable<BaseResponse<CircleListEntity>> searchCircleList(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchCircleList(headers, params);
    }

    @Override
    public Observable<BaseResponse<MasterDetailEntity>> userDetail(Map<String, String> headers, Map<String, String> params) {
        return mApiService.userDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<FansFocusEntity>> myFans(Map<String, String> headers, Map<String, String> params) {
        return mApiService.myFans(headers, params);
    }

    @Override
    public Observable<BaseResponse<FansFocusEntity>> myAttention(Map<String, String> headers, Map<String, String> params) {
        return mApiService.myAttention(headers, params);
    }

    @Override
    public Observable<BaseResponse<SearchResultEntity>> searchUserCircle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchUserCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<CollectEntity>> myCollect(Map<String, String> headers, Map<String, String> params) {
        return mApiService.myCollect(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> attention(Map<String, String> headers, Map<String, String> params) {
        return mApiService.attention(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> userInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.userInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updateUserInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.updateUserInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updatePhone(Map<String, String> headers, Map<String, String> params) {
        return mApiService.updatePhone(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> questionFeedback(Map<String, String> headers, Map<String, String> params) {
        return mApiService.questionFeedback(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<AleadyBuyEntity>>> alreadyBuy(Map<String, String> headers, Map<String, String> params) {
        return mApiService.alreadyBuy(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> checkisBind(Map<String, String> headers, Map<String, String> params) {
        return mApiService.checkisBind(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> checkMobileCodeByAPP(Map<String, String> headers, Map<String, String> params) {
        return mApiService.checkMobileCodeByAPP(headers, params);
    }

    @Override
    public Observable<BaseResponse<CourseListEntity>> getCourseList(Map<String, String> headers, Map<String, String> params) {
        return mApiService.getCourseList(headers, params);
    }

    @Override
    public Observable<BaseResponse<PayOrderEntity>> payOrder(Map<String, String> headers, Map<String, String> params) {
        return mApiService.payOrder(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> updateCircleInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.updateCircleInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<VersionRecordEntity>>> versionRecord(Map<String, String> headers, Map<String, String> params) {
        return mApiService.versionRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<ActivityEntity>> getActivity() {
        return mApiService.getActivity();
    }

    @Override
    public Observable<BaseResponse<IEntity>> addChoiceness(Map<String, String> headers, Map<String, String> params) {
        return mApiService.addChoiceness(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> getThumb(Map<String, String> headers, Map<String, String> params) {
        return mApiService.getThumb(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> userShieldRecord(Map<String, String> headers, Map<String, String> params) {
        return mApiService.userShieldRecord(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<DissertationEntity>>> searchDissertation() {
        return mApiService.searchDissertation();
    }

    @Override
    public Observable<BaseResponse<DissertationDetailEntity>> searchDissertationDetail(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchDissertationDetail(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> idCardUploadHandle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.idCardUploadHandle(headers, params);
    }

    @Override
    public Observable<BaseResponse<CardInfoEntity>> checkCardInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.checkCardInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<CardInfoEntity>> searchUserAttestationInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchUserAttestationInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> updateCardInfo(Map<String, String> headers, Map<String, String> params) {
        return mApiService.updateCardInfo(headers, params);
    }

    @Override
    public Observable<BaseResponse<DissertationListEntity>> searchDissertation1(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchDissertation1(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> complain(Map<String, String> headers, Map<String, String> params) {
        return mApiService.complain(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> pdfUploadHandle(List<MultipartBody.Part> files) {
        return mApiService.pdfUploadHandle(files);
    }

    @Override
    public Observable<BaseResponse<File>> downloadFile(String url) {
        return mApiService.downloadFile(url);
    }

    @Override
    public Observable<BaseResponse<IEntity>> themeTopOperate(Map<String, String> headers, Map<String, String> params) {
        return mApiService.themeTopOperate(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<SongsEntity>>> searchAudioList(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchAudioList(headers, params);
    }

    @Override
    public Observable<BaseResponse<User>> getMobile(Map<String, String> headers, Map<String, String> params) {
        return mApiService.getMobile(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> themeRetract(Map<String, String> headers, Map<String, String> params) {
        return mApiService.themeRetract(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> myWallet(Map<String, String> headers, Map<String, String> params) {
        return mApiService.myWallet(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity>> searchFindTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchFindTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<String>> getClientUploadSign(Map<String, String> headers, Map<String, String> params) {
        return mApiService.getClientUploadSign(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<IEntity>>> chooseCircle(Map<String, String> headers, Map<String, String> params) {
        return mApiService.chooseCircle(headers, params);
    }

    @Override
    public Observable<BaseResponse<ShortVideoEntity>> searchVideoTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchVideoTheme(headers, params);
    }

    @Override
    public Observable<BaseResponse<IEntity>> themeVideoBrowse(Map<String, String> headers, Map<String, String> params) {
        return mApiService.themeVideoBrowse(headers, params);
    }

    @Override
    public Observable<BaseResponse<ThemeInfoEntity>> findThemeSearch(Map<String, String> headers, Map<String, String> params) {
        return mApiService.findThemeSearch(headers, params);
    }

    @Override
    public Observable<BaseResponse<List<ThemeInfoEntity.ThemeInfoBean>>> searchChoicenessTheme(Map<String, String> headers, Map<String, String> params) {
        return mApiService.searchChoicenessTheme(headers, params);
    }
}
