package com.bjjy.buildtalk.ui.main;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/27 11:18 AM
 * @project BuildTalk
 * @description:
 */
public class PhoneLoginPresenter extends BasePresenter<PhoneLoginContract.View> {

    @Inject
    public PhoneLoginPresenter() {

    }

    public void senSms(String mobile) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.MOBILE, mobile);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.sendSms(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        ToastUtils.showShort("发送成功");
                    }
                }));
    }


    public void mobileRegister(String mobile, String checkCode) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.MOBILE, mobile);
        paramas.put("checkCode", checkCode);
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.mobileRegister(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(userBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<User>(mView) {
                    @Override
                    public void onSuccess(User user) {
                        user.setLoginStatus(true);
                        mDataManager.addUser(user);
                        mView.handlerLoginSuccess(user);
                    }
                }));
    }
}
