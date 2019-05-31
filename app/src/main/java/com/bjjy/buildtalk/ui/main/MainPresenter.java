package com.bjjy.buildtalk.ui.main;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/25 4:20 PM
 * @project BuildTalk
 * @description:
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void test() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("name", "android");
        paramas.put("phone", "123");
        paramas.put("age","");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.signTest(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(listBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<List<IEntity>>(mView){
                    @Override
                    public void onSuccess(List<IEntity> testEntities) {
                        LogUtils.e(testEntities.get(0).getAuthor_name());
                    }
                }));
    }
}
