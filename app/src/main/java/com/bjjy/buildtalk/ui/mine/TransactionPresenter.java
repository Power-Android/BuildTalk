package com.bjjy.buildtalk.ui.mine;

import android.text.TextUtils;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.bjjy.buildtalk.entity.TransactionTabEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/29 9:41 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionPresenter extends BasePresenter<TransactionContract.View> implements TransactionContract.Presenter {

    @Inject
    public TransactionPresenter() {

    }

    public void setTab() {
        List<TransactionTabEntity> list = new ArrayList<>();
        list.add(new TransactionTabEntity("全部", true));
        list.add(new TransactionTabEntity("课程", false));
        list.add(new TransactionTabEntity("文章", false));

        mView.handlerTab(list);
    }

    public void setRecord(String type, String title) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("type",type);
        if (TextUtils.isEmpty(title)){
            paramas.put("search_type", "1");
        }else {
            paramas.put("search_type", "2");
        }
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.alreadyBuy(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<List<AleadyBuyEntity>>(mView, false) {
                    @Override
                    public void onSuccess(List<AleadyBuyEntity> list) {
                        mView.handlerList(list);
                    }
                }));
    }
}
