package com.bjjy.buildtalk.core.http.api;


import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
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
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers(Constants.HEADER_PASSID)
    @POST("test")
    @FormUrlEncoded
    Observable<BaseResponse<List<TestEntity>>> signTest(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> paramas);

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
    Observable<BaseResponse<TestEntity>> praiseRecord(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * @param headers
     * @param params
     * @return 新闻，文稿点赞
     */
    @Headers(Constants.HEADER_PASSID)
    @POST("collectarticle")
    @FormUrlEncoded
    Observable<BaseResponse<TestEntity>> collectArticle(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);


//
//    /**
//     * 广告栏
//     * https://www.wanandroid.com/banner/json
//     *
//     * @return 广告栏数据
//     */
//    @GET("banner/json")
//    Observable<BaseResponse<List<BannerData>>> getBannerData();
//
//
//    /**
//     * 登录
//     * https://www.wanandroid.com/user/login
//     *
//     * @param username user name
//     * @param password password
//     * @return 登录数据
//     */
//    @POST("user/login")
//    @FormUrlEncoded
//    Observable<BaseResponse<LoginData>> login(@Field("username") String username, @Field("password") String password);
//
//    /**
//     * 注册
//     * https://www.wanandroid.com/user/register
//     *
//     * @param username   user name
//     * @param password   password
//     * @param repassword re password
//     * @return 注册数据
//     */
//    @POST("user/register")
//    @FormUrlEncoded
//    Observable<BaseResponse<LoginData>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
//
//    /**
//     * 获取收藏列表
//     * https://www.wanandroid.com/lg/collect/list/0/json
//     *
//     * @param page page number
//     * @return 收藏列表数据
//     */
//    @GET("lg/collect/list/{page}/json")
//    Observable<BaseResponse<ArticleListData>> getCollectList(@Path("page") int page);
//
//    /**
//     * 知识体系下的文章
//     * https://www.wanandroid.com/article/list/0?cid=60
//     *
//     * @param page page num
//     * @param cid  second page id
//     * @return 知识体系文章数据
//     */
//    @GET("article/list/{page}/json")
//    Observable<BaseResponse<ArticleListData>> getKnowledgeListData(@Path("page") int page, @Query("cid") int cid);
//
//
//    /**
//     * 获取TODO列表
//     * https://www.wanandroid.com/lg/todo/v2/list/{page}/json
//     * <p>
//     * 页码从1开始，拼接在url 上
//     * status 状态， 1-完成；0未完成; 默认全部展示；
//     * type 创建时传入的类型, 默认全部展示
//     * priority 创建时传入的优先级；默认全部展示
//     * orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；（1和2只能获取到已完成的TODO）
//     *
//     * @return
//     */
//    @GET("lg/todo/v2/list/{page}/json")
//    Observable<BaseResponse<TodoListData>> getTodoListData(@Path("page") int page, @QueryMap Map<String, Object> map);
//
//    /**
//     * 新增一条TODO
//     * https://www.wanandroid.com/lg/todo/add/json
//     * <p>
//     * title: 新增标题（必须）
//     * content: 新增详情（可选）
//     * date: 2018-08-01 预定完成时间（不传默认当天，建议传）
//     * type: 大于0的整数（可选）；
//     * priority 大于0的整数（可选）；
//     *
//     * @return
//     */
//    @POST("lg/todo/add/json")
//    @FormUrlEncoded
//    Observable<BaseResponse<TodoItemData>> addTodo(@FieldMap Map<String, Object> map);
//

}
