package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.DissertationListEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019-11-19 09:35
 * @project BuildTalk
 * @description:
 */
public class DissertationListPresenter extends BasePresenter<DissertationListContract.View> implements DissertationListContract.Presenter {

    @Inject
    public DissertationListPresenter() {

    }

    public void searchDissertation(int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(App.getContext().getString(R.string.PAGE),page+"");
        paramas.put(App.getContext().getString(R.string.PAGE_SIZE),"10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchDissertation1(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(courseEntity -> mView != null)
                .subscribeWith(new BaseObserver<DissertationListEntity>(mView,false){
                    @Override
                    public void onSuccess(DissertationListEntity dissertationListEntities) {
                        mView.handlerList(dissertationListEntities, isRefresh);
                    }
                }));
    }
}
