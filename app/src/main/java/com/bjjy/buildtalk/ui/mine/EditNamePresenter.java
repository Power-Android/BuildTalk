package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
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
 * @date 2019/6/26 11:14 AM
 * @project BuildTalk
 * @description:
 */
public class EditNamePresenter extends BasePresenter<EditNameContract.View> {

    @Inject
    public EditNamePresenter() {

    }

    public void editName(String nickName) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("type", "1");
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("nickName", nickName);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.updateUserInfo(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(stringBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {

                    }

                    @Override
                    public void onNext(BaseResponse<IEntity> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getErrorMsg().equals("更改名字成功")){
                            User user = mDataManager.getUser();
                            user.setNickName(nickName);
                            mDataManager.addUser(user);
                            mView.handlerUpData(nickName);
                        }
                    }
                }));
    }
}
