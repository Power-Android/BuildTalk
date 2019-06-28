package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/28 9:42 AM
 * @project BuildTalk
 * @description:
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    @Inject
    public SettingPresenter() {

    }

    public void wechatBindPhone(String unionid, String openid, String name, String iconurl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.MOBILE, mDataManager.getUser().getMobile());
        paramas.put("type", "2");
        paramas.put("checkCode", "2");
        paramas.put("openId", openid);
        paramas.put("unionId", unionid);
        paramas.put("headImage", iconurl);
        paramas.put("nickName", name);
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.checkMobileCodeByAPP(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<User>(mView, false) {
                    @Override
                    public void onSuccess(User user) {
                        user.setLoginStatus(true);
                        mDataManager.addUser(user);
                        mView.handlerBind();
                    }
                }));
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

                    }

                    @Override
                    public void onNext(BaseResponse<User> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getErrorCode() == 0){
                            wechatBindPhone(unionid, openid, iconurl, name);
                        }else {
                            ToastUtils.showShort("微信已绑定过");
                        }
                    }
                }));
    }
}
