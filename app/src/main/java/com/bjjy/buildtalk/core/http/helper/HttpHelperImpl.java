

package com.bjjy.buildtalk.core.http.helper;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.http.api.ApiService;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
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
    public Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(Map<String, String> headers, Map<String, String> params) {
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
    public Observable<BaseResponse<IEntity>> createCircle(Map<String, String> headers, Map<String, String> params) {
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

    //
//    @Override
//    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
//        return mApiService.getBannerData();
//    }
//
//    @Override
//    public Observable<BaseResponse<List<ArticleItemData>>> getTopArticles() {
//        return mApiService.getTopArticles();
//    }
//
//    @Override
//    public Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites() {
//        return mApiService.getUsefulSites();
//    }
//
//    @Override
//    public Observable<BaseResponse<List<TopSearchData>>> getTopSearchData() {
//        return mApiService.getTopSearchData();
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> getSearchResultList(int pageNum, String k) {
//        return mApiService.getSearchResultList(pageNum, k);
//    }
//
//    @Override
//    public Observable<BaseResponse<LoginData>> login(String username, String password) {
//        return mApiService.login(username, password);
//    }
//
//    @Override
//    public Observable<BaseResponse<LoginData>> register(String username, String password, String repassword) {
//        return mApiService.register(username, password, repassword);
//    }
//
//    @Override
//    public Observable<BaseResponse<LoginData>> logout() {
//        return mApiService.logout();
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> addCollectArticle(int id) {
//        return mApiService.addCollectArticle(id);
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> addCollectOutsideArticle(String title, String author, String link) {
//        return mApiService.addCollectOutsideArticle(title, author, link);
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> getCollectList(int page) {
//        return mApiService.getCollectList(page);
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> cancelCollectArticle(int id) {
//        return mApiService.cancelCollectArticle(id);
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> cancelCollectInCollectPage(int id, int originId) {
//        return mApiService.cancelCollectInCollectPage(id, originId);
//    }
//
//    @Override
//    public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
//        return mApiService.getNavigationListData();
//    }
//
//    @Override
//    public Observable<BaseResponse<List<ProjectTreeData>>> getProjectTreeData() {
//        return mApiService.getProjectTreeData();
//    }
//
//    @Override
//    public Observable<BaseResponse<ArticleListData>> getProjectListData(int page, int cid) {
//        return mApiService.getProjectListData(page, cid);
//    }
//
//    @Override
//    public Observable<BaseResponse<List<WxChapterData>>> getWxChapterListData() {
//        return mApiService.getWxChapterListData();
//    }
//

}
