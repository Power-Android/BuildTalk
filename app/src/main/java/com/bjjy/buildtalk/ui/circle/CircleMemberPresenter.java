package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/6/12 10:18 AM
 * @project BuildTalk
 * @description:
 */
public class CircleMemberPresenter extends BasePresenter<CircleMemberContract.View> {

    @Inject
    public CircleMemberPresenter() {

    }

    public void circleUser(String circle_id, int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("circle_id", circle_id);
        paramas.put(Constants.PAGE, page+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.circleUser(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<MemberEntity>(mView, false) {
                    @Override
                    public void onSuccess(MemberEntity memberEntity) {
                        mView.handlerCircleUser(memberEntity, isRefresh);
                    }
                }));
    }
}
