package com.bjjy.buildtalk.contains.discover;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DiscoverAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.bjjy.buildtalk.contains.discover.DiscoverFragment.COURSE_PAGE;
import static com.bjjy.buildtalk.contains.discover.DiscoverFragment.HOT_TOPTIC_PAGE;

/**
 * @author power
 * @date 2019/4/26 4:54 PM
 * @project BuildTalk
 * @description:
 */
public class DiscoverPresenter extends BasePresenter<DiscoverContract.View> implements DiscoverContract.Presenter {

    private int mTpoticPageCount = 1;
    private int mCoursePageCount = 1;

    @Inject
    public DiscoverPresenter() {
    }

    public void discoverType(List<DiscoverEntity> discoverEntityList) {
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_BANNER));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_EVERYDAY_TALK));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_HOT_TOPTIC));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_COURSE));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_PROJECT));

        mView.handlerDiscoverType(discoverEntityList);
    }

    public void discoverBanner() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("show", "index");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.discoverBanner(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(bannerEntity -> mView != null)
                .subscribeWith(new BaseObserver<List<BannerEntity>>(mView){
                    @Override
                    public void onSuccess(List<BannerEntity> bannerEntities) {
                        mView.handlerBanner(bannerEntities);
                    }
                }));
    }

    public void discoverEveryTalk() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("show", "index");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.everyDayTalk(headers,paramas)
            .compose(RxUtils.SchedulerTransformer())
            .filter(EveryTalkEntity -> mView != null)
            .subscribeWith(new BaseObserver<List<EveryTalkEntity>>(mView){
                @Override
                public void onSuccess(List<EveryTalkEntity> everyTalkEntities) {
                    mView.handlerEveryTalk(everyTalkEntities);
                }
            }));
    }

    public void discoverToptic() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("type", "1");
        if (HOT_TOPTIC_PAGE <= mTpoticPageCount){
            paramas.put(App.getContext().getString(R.string.PAGE),HOT_TOPTIC_PAGE+"");
        }else {
            HOT_TOPTIC_PAGE = 1;
            paramas.put("page",HOT_TOPTIC_PAGE+"");
        }
        paramas.put(App.getContext().getString(R.string.PAGE_SIZE),"3");
        paramas.put("isIndex","1");
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
                        mTpoticPageCount = courseEntities.getPage_count();
                        mView.handlerToptic(courseEntities);
                    }
                }));
    }

    public void discoverCourse() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("type", "2");
        if (COURSE_PAGE <= mCoursePageCount){
            paramas.put(App.getContext().getString(R.string.PAGE),COURSE_PAGE+"");
        }else {
            COURSE_PAGE = 1;
            paramas.put(App.getContext().getString(R.string.PAGE),COURSE_PAGE+"");
        }
        paramas.put(App.getContext().getString(R.string.PAGE_SIZE),"4");
        paramas.put("isIndex","1");
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
                        mCoursePageCount = courseEntities.getPage_count();
                        mView.handlerCourse(courseEntities);
                    }
                }));
    }
}
