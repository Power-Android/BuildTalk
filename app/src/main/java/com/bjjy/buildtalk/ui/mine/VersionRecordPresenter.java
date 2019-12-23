package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.VersionRecordEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019-09-02 14:31
 * @project BuildTalk
 * @description:
 */
public class VersionRecordPresenter extends BasePresenter<VersionRecordContarct.View> implements VersionRecordContarct.Presenter{

    @Inject
    public VersionRecordPresenter() {

    }

    public void versionRecord(String appVersionName) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("update_version", appVersionName);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);
        addSubscribe(mDataManager.versionRecord(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<List<VersionRecordEntity>>(mView,false){
                    @Override
                    public void onSuccess(List<VersionRecordEntity> versionRecordEntities) {
                        for (int i = 0; i < versionRecordEntities.size(); i++) {
                            if (i == 0){
                                versionRecordEntities.get(i).setItemType(0);
                            }else {
                                versionRecordEntities.get(i).setItemType(1);
                            }
                        }
                        mView.handlerVersionRecord(versionRecordEntities);
                    }
                }));
    }
}
