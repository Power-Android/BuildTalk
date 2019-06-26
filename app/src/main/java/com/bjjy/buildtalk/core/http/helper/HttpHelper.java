package com.bjjy.buildtalk.core.http.helper;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
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
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.HeaderMap;


/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public interface HttpHelper {

    Observable<BaseResponse<List<IEntity>>> signTest(Map<String, String> headers, Map<String, String> paramas);

    Observable<BaseResponse<List<BannerEntity>>> discoverBanner(Map<String, String> headers, Map<String, String> paramas);

    Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(Map<String, String> headers, Map<String, String> paramas);

    Observable<BaseResponse<CourseEntity>> courseInfo(Map<String, String> headers, Map<String, String> paramas);

    Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(Map<String, String> headers, Map<String, String> paramas);

    Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<GuestBookEntity>> guestBookList(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> praiseRecord(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> collectArticle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> sendSms(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<User>> mobileRegister(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<CircleEntity>> myCircle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<String>> uploadFiles(MultipartBody.Part file);

    Observable<BaseResponse<List<CircleTagEntity>>> searchCircleTags(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> createCircle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<SearchResultEntity>> searchHistory(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<CircleInfoEntity>> circleInfo(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<ThemeInfoEntity>> themeInfo(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<String>> uploadFiles(List<MultipartBody.Part> files);

    Observable<BaseResponse<IEntity>> joinCircle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<CommentSuccessEntity>> publishComment(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<PraiseEntity>> themeParise(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> publishTheme(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> updateTheme(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> collectTheme(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> deleteTheme(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<CircleMasterInfoEntity>> circleMasterInfo(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> quitCircle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<MyCardEntity>> myCard(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<MemberEntity>> circleUser(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<SearchCircleInfoEntity>> searchCircleInfo(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> searchThemeDetail(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> commentPageHandle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> deleteGuestbook(Map<String, String> headers,Map<String, String> params);

    Observable<BaseResponse<SearchResultEntity>> searchTalkHistory(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IndustryMasterEntity>> searchIndustryMaster(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<List<CircleMasterEntity>>> searchCircleMaster(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<MasterListEntity>> searchAuthor(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<CircleListEntity>> searchCircleList(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<MasterDetailEntity>> userDetail(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<FansFocusEntity>> myFans(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<FansFocusEntity>> myAttention(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<SearchResultEntity>> searchUserCircle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<CollectEntity>> myCollect(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> attention(Map<String, String> headers, Map<String, String> params);


}
