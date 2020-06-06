package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.tencent.qcloud.ugckit.utils.Constants;
import com.tencent.qcloud.ugckit.utils.HeaderUtils;
import com.tencent.qcloud.ugckit.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2020/5/29 11:01 AM
 * @project BuildTalk
 * @description:
 */
public class VideoPublishPresenter extends BasePresenter<VideoPublishContract.View> implements VideoPublishContract.Presenter {
    @Inject
    public VideoPublishPresenter() {
    }

    public void getSign() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);
        headers.put(Constants.PASS_ID, Constants.HEADER_PASSID);

        addSubscribe(mDataManager.getClientUploadSign(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, true, false) {
                    @Override
                    public void onSuccess(String sign) {
                        mView.handlerSignSuccess(sign);
                    }
                }));
    }
}
