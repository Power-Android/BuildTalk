package com.bjjy.buildtalk.ui.circle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/24 10:29 AM
 * @project BuildTalk
 * @description:
 */
public class CourseCirclePresenter extends BasePresenter<CourseCircleContract.View> implements CourseCircleContract.Presenyer {

    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    private List<Integer> mBadgeCountList = new ArrayList<>();
    private View mThemeView, mEssenceView;

    @Inject
    public CourseCirclePresenter() {

    }

    public void tabData() {
        mTitleList.add(App.getContext().getString(R.string.theme));
        mTitleList.add(App.getContext().getString(R.string.essence));

        mBadgeCountList.add(0);
        mBadgeCountList.add(0);

        mThemeView = LayoutInflater.from(App.getContext()).inflate(R.layout.circle_toptic_theme, null, false);
        mEssenceView = LayoutInflater.from(App.getContext()).inflate(R.layout.circle_toptic_essence, null, false);
        mViews.add(mThemeView);
        mViews.add(mEssenceView);

        mView.handlerTab(mTitleList, mViews, mBadgeCountList);

    }

    public void CircleInfo(String circle_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
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

    public void themeInfo(String circle_id, int page, String type, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
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
                        if (themeInfoEntity.getThemeInfo().size() > 0){
                            mView.handlerThemeInfo(themeInfoEntity, isRefresh);
                        }
                    }
                }));
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
}
