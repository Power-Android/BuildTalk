package com.bjjy.buildtalk.contains.discover;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/6 5:53 PM
 * @project BuildTalk
 * @description:
 */
public class CourseListPresenter extends BasePresenter<CourseListContract.View> implements CourseListContract.Presenter {

    @Inject
    public CourseListPresenter() {

    }

    public void courseList(int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("type", "2");
        paramas.put(App.getContext().getString(R.string.PAGE),page+"");
        paramas.put(App.getContext().getString(R.string.PAGE_SIZE),"10");
        paramas.put("isIndex","0");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.courseInfo(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(courseEntity -> mView != null)
                .subscribeWith(new BaseObserver<CourseEntity>(mView,false){
                    @Override
                    public void onSuccess(CourseEntity courseEntities) {
                        mView.handlerCourseList(courseEntities,isRefresh);
                    }
                }));
    }
}
