package com.bjjy.buildtalk.core.http.api;


import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
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

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    @Headers(Constants.HEADER_PASSID)
    @POST("test")
    @FormUrlEncoded
    Observable<BaseResponse<List<IEntity>>> signTest(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> paramas);

    /**
     * @return 首页轮播图
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getIndexPic")
    @FormUrlEncoded
    Observable<BaseResponse<List<BannerEntity>>> discoverBanner(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 首页每日一谈
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchRecommendNews")
    @FormUrlEncoded
    Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 热门话题和精品课程（首页和列表）
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleInfoByType")
    @FormUrlEncoded
    Observable<BaseResponse<CourseEntity>> courseInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 每日一谈列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAllNews")
    @FormUrlEncoded
    Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 每日一谈详情
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchArticleInfo")
    @FormUrlEncoded
    Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取留言
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchManyGuestbook")
    @FormUrlEncoded
    Observable<BaseResponse<GuestBookEntity>> guestBookList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 发表留言
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("saveguestbook")
    @FormUrlEncoded
    Observable<BaseResponse<SaveRecordEntity>> saveRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 留言点赞
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("guestbookPraise")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> praiseRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 新闻，文稿点赞
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("collectarticle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> collectArticle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 发送短信
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("sendSms")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> sendSms(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 手机号登录
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("mobileRegister")
    @FormUrlEncoded
    Observable<BaseResponse<User>> mobileRegister(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 我的圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myCircle")
    @FormUrlEncoded
    Observable<BaseResponse<CircleEntity>> myCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * 使用@Multipart注解方法，并用@Part注解方法参数，类型是List<okhttp3.MultipartBody.Part>
     * @return 上传单张图片
     */
    @Multipart
    @POST("picuploadhandle")
    Observable<BaseResponse<String>> uploadFiles(@Part MultipartBody.Part file);

    /**
     * 使用@Multipart注解方法，并用@Part注解方法参数，类型是List<okhttp3.MultipartBody.Part>
     * @return 上传多张图片
     */
    @Multipart
    @POST("manyPicUploadHandle")
    Observable<BaseResponse<String>> uploadFiles(@Part List<MultipartBody.Part> files);

    /**
     * 使用@Multipart注解方法，并用@Part注解方法参数，类型是List<okhttp3.MultipartBody.Part>
     * @return 上传PDF文件
     */
    @Multipart
    @POST("pdfUploadHandle")
    Observable<BaseResponse<String>> pdfUploadHandle(@Part List<MultipartBody.Part> files);

    /**
     * 下载文件
     * @param url
     * @return
     * 10以上用@streaming。不会造成oom，反正你用就是了
     */
    @GET
    @Streaming
    Observable<BaseResponse<File>> downloadFile(@Url String url);

    /**
     * @return 获取可用标签
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleTags")
    @FormUrlEncoded
    Observable<BaseResponse<List<CircleTagEntity>>> searchCircleTags(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 创建圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("createCircle")
    @FormUrlEncoded
    Observable<BaseResponse<String>> createCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 创建圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("updateCircleInfo")
    @FormUrlEncoded
    Observable<BaseResponse<String>> updateCircleInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 圈子搜索列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleInfoBykeywords")
    @FormUrlEncoded
    Observable<BaseResponse<SearchResultEntity>> searchHistory(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 话题圈、课程圈（话题圈课程圈的圈子信息）
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAllCircleInfo")
    @FormUrlEncoded
    Observable<BaseResponse<CircleInfoEntity>> circleInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题分类查看(话题圈评论列表)
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getThemeInfoByType")
    @FormUrlEncoded
    Observable<BaseResponse<ThemeInfoEntity>> themeInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 加入话题圈
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("joinCircle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> joinCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);


    /**
     * @return 发表主题评论
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("publishThemeComment")
    @FormUrlEncoded
    Observable<BaseResponse<CommentSuccessEntity>> publishComment(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题点赞
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("themeParise")
    @FormUrlEncoded
    Observable<BaseResponse<PraiseEntity>> themeParise(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 发表主题
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("publishTheme")
    @FormUrlEncoded
    Observable<BaseResponse<String>> publishTheme(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 修改主题
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("updateTheme")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> updateTheme(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 收藏主题
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("collectTheme")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> collectTheme(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 删除主题
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("deleteTheme")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> deleteTheme(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 根据圈子id查询圈主、成员信息
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("circleMasterInfo")
    @FormUrlEncoded
    Observable<BaseResponse<CircleMasterInfoEntity>> circleMasterInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 退出圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("quitCircle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> quitCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 我的名片
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myCard")
    @FormUrlEncoded
    Observable<BaseResponse<MyCardEntity>> myCard(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 圈子成员
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("circleUser")
    @FormUrlEncoded
    Observable<BaseResponse<MemberEntity>> circleUser(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 圈子资料
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleInfo")
    @FormUrlEncoded
    Observable<BaseResponse<SearchCircleInfoEntity>> searchCircleInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题详情
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchThemeDetail")
    @FormUrlEncoded
    Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> searchThemeDetail(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题全部评论
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("commentPageHandle")
    @FormUrlEncoded
    Observable<BaseResponse<ThemeInfoEntity.ThemeInfoBean>> commentPageHandle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 删除留言
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("deleteGuestbook")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> deleteGuestbook(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 搜索大咖
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAuthorByKeyword")
    @FormUrlEncoded
    Observable<BaseResponse<SearchResultEntity>> searchTalkHistory(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 行业大咖
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchIndustryMaster")
    @FormUrlEncoded
    Observable<BaseResponse<IndustryMasterEntity>> searchIndustryMaster(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 人气圈主
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleMaster")
    @FormUrlEncoded
    Observable<BaseResponse<List<CircleMasterEntity>>> searchCircleMaster(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 大咖列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAuthor")
    @FormUrlEncoded
    Observable<BaseResponse<MasterListEntity>> searchAuthor(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 人气圈主排行
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchCircleMaster")
    @FormUrlEncoded
    Observable<BaseResponse<CircleListEntity>> searchCircleList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 大咖详情
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("userDetail")
    @FormUrlEncoded
    Observable<BaseResponse<MasterDetailEntity>> userDetail(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 粉丝
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myFans")
    @FormUrlEncoded
    Observable<BaseResponse<FansFocusEntity>> myFans(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 我的关注
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myAttention")
    @FormUrlEncoded
    Observable<BaseResponse<FansFocusEntity>> myAttention(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 查询用户创建的圈子、加入的圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchUserCircle")
    @FormUrlEncoded
    Observable<BaseResponse<SearchResultEntity>> searchUserCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 收藏
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myCollect")
    @FormUrlEncoded
    Observable<BaseResponse<CollectEntity>> myCollect(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 关注、取消关注
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("attentionUserOperate")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> attention(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 查询个人信息、个人主页
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchUserInfoByUser_id")
    @FormUrlEncoded
    Observable<BaseResponse<User>> userInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 更改名字、头像、背景
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("updateUserInfo")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> updateUserInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 更改手机号
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("updatePhone")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> updatePhone(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 问题反馈
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("questionFeedback")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> questionFeedback(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 交易明细
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("alreadyBuy")
    @FormUrlEncoded
    Observable<BaseResponse<List<AleadyBuyEntity>>> alreadyBuy(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 是否绑定手机号
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("checkisBindMobileByApp")
    @FormUrlEncoded
    Observable<BaseResponse<User>> checkisBind(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 绑定手机号
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("checkMobileCodeByAPP")
    @FormUrlEncoded
    Observable<BaseResponse<User>> checkMobileCodeByAPP(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取课程目录
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getCourseList")
    @FormUrlEncoded
    Observable<BaseResponse<CourseListEntity>> getCourseList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 微信支付 返回微信支付所需参数
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("unifiedapporder")
    @FormUrlEncoded
    Observable<BaseResponse<PayOrderEntity>> payOrder(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 版本记录
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchUpdateRecord")
    @FormUrlEncoded
    Observable<BaseResponse<List<VersionRecordEntity>>> versionRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 首页活动弹框
     */
    @GET("getActivity")
    Observable<BaseResponse<ActivityEntity>> getActivity();

    /**
     * @return 主题加精
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("themeChoicenessOperate")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> addChoiceness(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题置顶
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("themeTopOperate")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> themeTopOperate(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题不喜欢
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("userShieldRecord")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> userShieldRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取缩略图
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getThumb")
    @FormUrlEncoded
    Observable<BaseResponse<String>> getThumb(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取所有专题
     */
    @GET("searchDissertation")
    Observable<BaseResponse<List<DissertationEntity>>> searchDissertation();

    /**
     * @return 查询专题详情
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchDissertationDetail")
    @FormUrlEncoded
    Observable<BaseResponse<DissertationDetailEntity>> searchDissertationDetail(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取身份证信息
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("idCardUploadHandle")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> idCardUploadHandle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 验证身份信息
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("checkCardInfo")
    @FormUrlEncoded
    Observable<BaseResponse<CardInfoEntity>> checkCardInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 查询用户认证信息(未认证、已认证)
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchUserAttestationInfo")
    @FormUrlEncoded
    Observable<BaseResponse<CardInfoEntity>> searchUserAttestationInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 修改大咖资料
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("updateCardInfo")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> updateCardInfo(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取所有专题
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchIndexDissertation")
    @FormUrlEncoded
    Observable<BaseResponse<DissertationListEntity>> searchDissertation1(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 投诉理由
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("complain")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> complain(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 音频列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchAudioList")
    @FormUrlEncoded
    Observable<BaseResponse<List<SongsEntity>>> searchAudioList(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取手机号
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getMobile")
    @FormUrlEncoded
    Observable<BaseResponse<User>> getMobile(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 主题收起/展开
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("themeRetract")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> themeRetract(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 我的钱包
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("myWallet")
    @FormUrlEncoded
    Observable<BaseResponse<String>> myWallet(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 发现页/关注页
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchFindTheme")
    @FormUrlEncoded
    Observable<BaseResponse<DisrOrAttenEntity>> searchFindTheme(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 获取腾讯签名
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("getClientUploadSign")
    @FormUrlEncoded
    Observable<BaseResponse<String>> getClientUploadSign(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);


    /**
     * @return 选择圈子
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("chooseCircle")
    @FormUrlEncoded
    Observable<BaseResponse<List<IEntity>>> chooseCircle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);


    /**
     * @return 视频播放列表
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("searchVideoTheme")
    @FormUrlEncoded
    Observable<BaseResponse<ShortVideoEntity>> searchVideoTheme(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 视频主题浏览记录
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("themeVideoBrowse")
    @FormUrlEncoded
    Observable<BaseResponse<IEntity>> themeVideoBrowse(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @return 发现页/关注页搜索
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("findThemeSearch")
    @FormUrlEncoded
    Observable<BaseResponse<DisrOrAttenEntity>> findThemeSearch(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

}
