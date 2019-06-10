package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.adapter.CircleAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/26 4:58 PM
 * @project BuildTalk
 * @description:
 */
public class CirclePresenter extends BasePresenter<CircleContract.View> implements CircleContract.Presenter {

    @Inject
    public CirclePresenter() {

    }

    public void circleList(int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.myCircle(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(circleEntity -> mView != null)
                .subscribeWith(new BaseObserver<CircleEntity>(mView, false) {
                    @Override
                    public void onSuccess(CircleEntity circleEntity) {
                        mView.handlerCircleList(circleEntity, isRefresh);
                    }
                }));
    }
}
