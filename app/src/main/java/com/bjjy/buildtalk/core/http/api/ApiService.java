package com.bjjy.buildtalk.core.http.api;


import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
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

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiService {

    @Headers(Constants.HEADER_PASSID)
    @POST("test")
    @FormUrlEncoded
    Observable<BaseResponse<List<IEntity>>> signTest(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> paramas);

    /**
     * @param headers
     * @param params
     * @return 首页轮播图
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getIndexPic")
    @FormUrlEncoded
    Observable<BaseResponse<List<BannerEntity>>> discoverBanner(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 首页每日一谈
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchRecommendNews")
    @FormUrlEncoded
    Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 热门话题和精品课程（首页和列表）
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleInfoByType")
    @FormUrlEncoded
    Observable<BaseResponse<CourseEntity>> courseInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 每日一谈列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAllNews")
    @FormUrlEncoded
    Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 每日一谈详情
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchArticleInfo")
    @FormUrlEncoded
    Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 获取留言
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("serachManyGuestbook")
    @FormUrlEncoded
    Observable<BaseResponse<GuestBookEntity>> guestBookList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 发表留言
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("saveguestbook")
    @FormUrlEncoded
    Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 留言点赞
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("guestbookPraise")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> praiseRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 新闻，文稿点赞
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("collectarticle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> collectArticle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 发送短信
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("sendSms")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> sendSms(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 手机号登录
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("mobileRegister")
    @FormUrlEncoded
    Observable<BaseResponse<User>> mobileRegister(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 我的圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myCircle")
    @FormUrlEncoded
    Observable<BaseResponse<CircleEntity>> myCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * 使用@Multipart注解方法，并用@Part注解方法参数，类型是List<okhttp3.MultipartBody.Part>
     * @return 上传图片
     */
    @Multipart
    @POST("picuploadhandle")
    Observable<BaseResponse<String>> uploadFiles(@Part MultipartBody.Part file);

    /**
     * 使用@Multipart注解方法，并用@Part注解方法参数，类型是List<okhttp3.MultipartBody.Part>
     * @return 上传图片
     */
    @Multipart
    @POST("manyPicUploadHandle")
    Observable<BaseResponse<String>> uploadFiles(@Part List<MultipartBody.Part> files);


    /**
     * @param headers
     * @param params
     * @return 获取可用标签
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleTags")
    @FormUrlEncoded
    Observable<BaseResponse<List<CircleTagEntity>>> searchCircleTags(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 创建圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("createCircle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> createCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 圈子搜索列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleInfoBykeywords")
    @FormUrlEncoded
    Observable<BaseResponse<SearchResultEntity>> searchHistory(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 话题圈、课程圈（话题圈课程圈的圈子信息）
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAllCircleInfo")
    @FormUrlEncoded
    Observable<BaseResponse<CircleInfoEntity>> circleInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 主题分类查看(话题圈评论列表)
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getThemeInfoByType")
    @FormUrlEncoded
    Observable<BaseResponse<ThemeInfoEntity>> themeInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 加入话题圈
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("joinCircle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> joinCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

}
