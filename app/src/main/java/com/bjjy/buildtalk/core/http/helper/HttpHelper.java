package com.bjjy.buildtalk.core.http.helper;

import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.contains.ArticleListData;
import com.bjjy.buildtalk.entity.TestEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public interface HttpHelper {

    //    /**
//     * 获取文章列表
//     *
//     * @param pageNum 页数
//     * @return 文章列表数据
//     */
    Observable<BaseResponse<ArticleListData>> getArticleList(int pageNum);

    Observable<BaseResponse<List<TestEntity>>> signTest(Map<String,String> headers, Map<String,String> paramas);
//
//    Observable<BaseResponse<List<BannerData>>> getBannerData();
//
//    Observable<BaseResponse<List<ArticleItemData>>> getTopArticles();
//
//    Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites();
//
//    Observable<BaseResponse<List<TopSearchData>>> getTopSearchData();
//
//    Observable<BaseResponse<ArticleListData>> getSearchResultList(int pageNum, String k);
//
//    Observable<BaseResponse<LoginData>> login(String username, String password);
//
//    Observable<BaseResponse<LoginData>> register(String username, String password, String repassword);
//
//    Observable<BaseResponse<LoginData>> logout();
//
//    Observable<BaseResponse<ArticleListData>> addCollectArticle(int id);
//
//    Observable<BaseResponse<ArticleListData>> addCollectOutsideArticle(String title, String author, String link);
//
//    Observable<BaseResponse<ArticleListData>> getCollectList(int page);
//
//    Observable<BaseResponse<ArticleListData>> cancelCollectArticle(int id);
//
//    Observable<BaseResponse<ArticleListData>> cancelCollectInCollectPage(int id, int originId);
//
//    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();
//
//    Observable<BaseResponse<List<ProjectTreeData>>> getProjectTreeData();
//
//    Observable<BaseResponse<ArticleListData>> getProjectListData(int page, int cid);
//
//    Observable<BaseResponse<List<WxChapterData>>> getWxChapterListData();
//
//    Observable<BaseResponse<ArticleListData>> getWxArticlesData(int id, int page);
//
//    Observable<BaseResponse<ArticleListData>> getWxSearchData(int id, int page, String k);
//
//    Observable<BaseResponse<List<KnowledgeTreeData>>> getKnowledgeTreeData();
//
//    Observable<BaseResponse<ArticleListData>> getKnowledgeListData(int page, int cid);
//
//    Observable<BaseResponse<TodoListData>> getTodoListData(int page, Map<String, Object> map);
//
//    Observable<BaseResponse<TodoItemData>> addTodo(Map<String, Object> map);
//
//    Observable<BaseResponse<TodoItemData>> updateTodo(int id, Map<String, Object> map);
//
//    Observable<BaseResponse<TodoItemData>> deleteTodo(int id);
//
//    Observable<BaseResponse<TodoItemData>> updateTodoStatus(int id, int status);

}
