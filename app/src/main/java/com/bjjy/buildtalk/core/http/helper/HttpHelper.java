package com.bjjy.buildtalk.core.http.helper;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.IEntity;

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

    Observable<BaseResponse<List<IEntity>>> signTest(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<List<BannerEntity>>> discoverBanner(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<List<EveryTalkEntity>>> everyDayTalk(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<CourseEntity>> courseInfo(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<EveryTalkListEntity>> everyDayTalkList(Map<String,String> headers, Map<String,String> paramas);

    Observable<BaseResponse<EveryTalkDetailEntity>> everyTalkDetail(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<GuestBookEntity>> guestBookList(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<List<SaveRecordEntity>>> saveRecord(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> praiseRecord(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> collectArticle(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<IEntity>> sendSms(Map<String, String> headers, Map<String, String> params);

    Observable<BaseResponse<User>> mobileRegister(Map<String, String> headers, Map<String, String> params);
}
