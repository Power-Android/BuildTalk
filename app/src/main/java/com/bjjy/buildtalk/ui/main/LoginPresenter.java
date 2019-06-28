package com.bjjy.buildtalk.ui.main;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/27 10:53 AM
 * @project BuildTalk
 * @description:
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> {

    @Inject
    public LoginPresenter() {
    }

    public void checkIsBind(String unionid, String openid, String name, String iconurl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("unionId",unionid);
        paramas.put("openId",openid);
        paramas.put("headImage",iconurl);
        paramas.put("nickName",name);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.checkisBind(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<User>(mView, false) {
                    @Override
                    public void onSuccess(User user) {
                        user.setLoginStatus(true);
                        mDataManager.addUser(user);
                        mView.handlerSuccess(user);
                    }

                    @Override
                    public void onNext(BaseResponse<User> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getErrorCode() == 0){
                            mView.handlerBindPhone("1",unionid, openid, iconurl, name);
                        }
                    }
                }));
    }
}
