package com.bjjy.buildtalk.ui.talk;

import android.view.LayoutInflater;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/14 4:28 PM
 * @project BuildTalk
 * @description:
 */
public class MasterCirclePresenter extends BasePresenter<MasterCircleContract.View> implements MasterCircleContract.Presenter {
    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    private View createView, joinView;
    @Inject
    public MasterCirclePresenter() {

    }

    public void tabData() {
        mTitleList.clear();
        mViews.clear();

        mTitleList.add("创建的");
        mTitleList.add("加入的");

        createView = LayoutInflater.from(App.getContext()).inflate(R.layout.circle_list_view, null, false);
        joinView = LayoutInflater.from(App.getContext()).inflate(R.layout.circle_list_view, null, false);

        mViews.add(createView);
        mViews.add(joinView);

        mView.handlerTab(mTitleList, mViews);
    }

    public void createList(String user_id, int create_page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("examine_user", mDataManager.getUser().getUser_id());
        paramas.put("circle_type", "1");
        paramas.put(Constants.USER_ID, user_id);
        paramas.put(Constants.PAGE , create_page+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchUserCircle(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<SearchResultEntity>(mView,false){
                    @Override
                    public void onSuccess(SearchResultEntity resultEntity) {
                        mView.handlerCreate(resultEntity, isRefresh);
                    }
                }));
    }

    public void joinList(String user_id, int join_page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("examine_user", mDataManager.getUser().getUser_id());
        paramas.put("circle_type", "2");
        paramas.put(Constants.USER_ID, user_id);
        paramas.put(Constants.PAGE , join_page+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchUserCircle(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<SearchResultEntity>(mView,false){
                    @Override
                    public void onSuccess(SearchResultEntity resultEntity) {
                        mView.handlerJoin(resultEntity, isRefresh);
                    }
                }));
    }
}
