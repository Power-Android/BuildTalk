package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/26 4:45 PM
 * @project BuildTalk
 * @description:
 */
public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    @Inject
    public MinePresenter() {
    }

    public void userInfo(String user_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, user_id);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.userInfo(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<User>(mView,false){
                    @Override
                    public void onSuccess(User user) {
                        user.setLoginStatus(true);
                        mDataManager.addUser(user);
                        mView.handlerUser(user);
                    }
                }));
    }

    public void myWallet() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.myWallet(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(guestBookEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, false) {
                    @Override
                    public void onSuccess(String s) {
                        mView.handlerWallet(s);
                    }
                }));
    }
}
