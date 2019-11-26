package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

import java.util.List;

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

    public void versionRecord() {
        addSubscribe(mDataManager.versionRecord()
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
