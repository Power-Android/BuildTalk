package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CommentSuccessEntity;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/7/1 4:34 PM
 * @project BuildTalk
 * @description:
 */
public class CourseDetailPresenter extends BasePresenter<CourseDetailContarct.View> {

    @Inject
    public CourseDetailPresenter() {

    }

    public void courseList(String circle_id, int coursePage) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("course_id", circle_id);
        paramas.put(Constants.PAGE, coursePage+"");
        paramas.put(Constants.PAGE_SIZE, "10");

        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.getCourseList(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<CourseListEntity>(mView, false) {
                    @Override
                    public void onSuccess(CourseListEntity courseListEntity) {
                        if (courseListEntity.getCourselist().size() > 0){
                            mView.handlerCourseList(courseListEntity);
                        }
                    }
                }));
    }

    public void themeInfo(String circle_id, int page, String type, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put("circle_id", circle_id);
        paramas.put("type_id", type);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.themeInfo(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<ThemeInfoEntity>(mView, false) {
                    @Override
                    public void onSuccess(ThemeInfoEntity themeInfoEntity) {
                            mView.handlerThemeInfo(themeInfoEntity, isRefresh);
                    }
                }));
    }

    public void praise(List<ThemeInfoEntity.ThemeInfoBean> data, int i) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("data_id", data.get(i).getTheme_id()+"");
        paramas.put("type_id", "1");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.themeParise(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<PraiseEntity>(mView, false) {
                    @Override
                    public void onSuccess(PraiseEntity praiseEntity) {
                        mView.handlerPraiseSuccess(data,i,praiseEntity);
                    }
                }));
    }

    public void collectTheme(ThemeInfoEntity.ThemeInfoBean data, int i) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", data.getTheme_id()+"");
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.collectTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerCollectSuccess(iEntity,data, i);
                    }
                }));
    }

    public void deleteTheme(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", data.getTheme_id()+"");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.deleteTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerDeleteSuccess(iEntity,data, i, list);
                    }
                }));
    }

    public void publishComment(String content, String theme_id, int i, List<ThemeInfoEntity.ThemeInfoBean> data) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("theme_id", theme_id);
        paramas.put("content", content);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.publishComment(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<CommentSuccessEntity>(mView, false) {
                    @Override
                    public void onSuccess(CommentSuccessEntity commentSuccessEntity) {
                        mView.handlerCommentSuccess(i,data, commentSuccessEntity.getCommentInfo());
                    }
                }));
    }

    public void payOrder(String type_id, int data_id, String circle_name, String course_money) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("type_id", type_id);
        paramas.put("data_id", data_id+"");
        paramas.put("order_name", circle_name);
        paramas.put("order_price", course_money);
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.payOrder(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<PayOrderEntity>(mView, true) {
                    @Override
                    public void onSuccess(PayOrderEntity payOrderEntity) {
                        mView.handlerWxOrder(payOrderEntity);
                    }
                }));
    }

    public void CircleInfo(String circle_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("circle_id", circle_id);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.circleInfo(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<CircleInfoEntity>(mView, false) {
                    @Override
                    public void onSuccess(CircleInfoEntity circleInfoEntity) {
                        mView.handlerCircleInfo(circleInfoEntity);
                    }
                }));
    }

    public void getThumb(String pic_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("pic_url", pic_url);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.getThumb(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, true, true) {
                    @Override
                    public void onSuccess(String thumb_url) {
                        mView.handlerThumbSuccess(thumb_url, data, i);
                    }
                }));
    }
}
