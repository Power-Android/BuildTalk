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
import com.bjjy.buildtalk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * @author power
 * @date 2019/6/11 1:54 PM
 * @project BuildTalk
 * @description:
 */
public class SetPicturePresenter extends BasePresenter<SetPictureContract.View> {

    @Inject
    public SetPicturePresenter() {

    }

    public void upload(MultipartBody.Part file, String type) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.uploadFiles(file)
                .compose(RxUtils.SchedulerTransformer())
                .filter(stringBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, false) {
                    @Override
                    public void onSuccess(String picUrl) {
                        updateUserInfo(picUrl, type);
                    }
                }));
    }

    private void updateUserInfo(String picUrl, String type) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("type", type);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        if ("2".equals(type)){
            paramas.put("headImage", picUrl);
        }else {
            paramas.put("bg_pic", picUrl);
        }
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
                        if (baseResponse.getErrorMsg().equals("更改头像成功")){
                            User user = mDataManager.getUser();
                            user.setHeadImage(picUrl);
                            mDataManager.addUser(user);
                            mView.handlerUpData(picUrl);
                        }else if (baseResponse.getErrorMsg().equals("更改背景成功")){
                            User user = mDataManager.getUser();
                            user.setBg_pic(picUrl);
                            mDataManager.addUser(user);
                            mView.handlerUpData(picUrl);
                        }
                    }
                }));
    }
}
