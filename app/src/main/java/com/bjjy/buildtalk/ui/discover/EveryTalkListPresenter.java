package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/5 1:41 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkListPresenter extends BasePresenter<EveryTalkContract.View> implements EveryTalkContract.Presenter {

    @Inject
    public EveryTalkListPresenter() {

    }

    @Override
    public void talkList(int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("page",page+"");
        paramas.put("page_size","10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.everyDayTalkList(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(EveryTalkListEntity -> mView != null)
                .subscribeWith(new BaseObserver<EveryTalkListEntity>(mView,false){
                    @Override
                    public void onSuccess(EveryTalkListEntity everyTalkListEntity) {
                        mView.handlerTalkList(everyTalkListEntity,isRefresh);
                    }
                }));
    }
}
