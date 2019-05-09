package com.bjjy.buildtalk.core.http.helper;

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


/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public interface HttpHelper {

    Observable<BaseResponse<List<TestEntity>>> signTest(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<List<BannerEntity>>> discoverBanner(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<CourseEntity>> courseInfo(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<TestEntity>> praiseRecord(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<TestEntity>> collectArticle(Map<String, String> headers, Map<String, String> params);

}
