package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019-11-19 13:52
 * @project BuildTalk
 * @description:
 */
public class ComplaintReasonPresenter extends BasePresenter<ComplaintReasonContract.View> implements ComplaintReasonContract.Presenter {

    @Inject
    public ComplaintReasonPresenter() {

    }

    public void complain(String data_id, String complain_content) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("data_id", data_id);
        paramas.put("complain_content", complain_content);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.complain(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, true, true){
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerComplain(iEntity);
                    }
                }));
    }
}
