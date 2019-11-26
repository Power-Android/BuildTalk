package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/10 1:59 PM
 * @project BuildTalk
 * @description:
 */
public class MasterListPresenter extends BasePresenter<MasterListContract.View> implements MasterListContract.Presenter {

    @Inject
    public MasterListPresenter() {
    }

    public void masterList(int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("show", "2");
        paramas.put(Constants.PAGE, page+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchAuthor(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<MasterListEntity>(mView,false){
                    @Override
                    public void onSuccess(MasterListEntity masterListEntity) {
                        mView.handlerMasterList(masterListEntity, isRefresh);
                    }
                }));
    }
}
