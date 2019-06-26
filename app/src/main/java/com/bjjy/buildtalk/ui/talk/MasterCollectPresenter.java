package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CollectEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/14 4:48 PM
 * @project BuildTalk
 * @description:
 */
public class MasterCollectPresenter extends BasePresenter<MasterCollectContract.View> implements MasterCollectContract.Presenter {

    @Inject
    public MasterCollectPresenter() {

    }

    public void myCollect(String user_id, int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("examine_user", mDataManager.getUser().getUser_id());
        paramas.put(Constants.USER_ID, user_id);
        paramas.put(Constants.PAGE , page+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.myCollect(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<CollectEntity>(mView,false){
                    @Override
                    public void onSuccess(CollectEntity collectEntity) {
                        mView.handlerCollect(collectEntity, isRefresh);
                    }
                }));
    }
}
