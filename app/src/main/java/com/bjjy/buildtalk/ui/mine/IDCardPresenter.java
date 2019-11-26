package com.bjjy.buildtalk.ui.mine;

import android.annotation.SuppressLint;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CardInfoEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * @author power
 * @date 2019-10-09 10:11
 * @project BuildTalk
 * @description:
 */
public class IDCardPresenter extends BasePresenter<IDCardContract.View> {

    @Inject
    public IDCardPresenter() {

    }

    public void upload(MultipartBody.Part file) {
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
                        mView.updateUserInfo(picUrl);
                    }
                }));
    }

    public void queryCard(String picUrl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("pic_url", picUrl);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.idCardUploadHandle(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, true, true){
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerQuery(iEntity, picUrl);
                    }
                }));
    }

    public void commiit(List<String> cardUrlList, String id, String name, String valid_data) {
        String pic_url = StringUtils.listToString3(cardUrlList);

        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("id", id);
        paramas.put("name", name);
        paramas.put("valid_date", valid_data);
        paramas.put("cardImage", pic_url);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.checkCardInfo(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<CardInfoEntity>(mView, true, true){
                    @Override
                    public void onSuccess(CardInfoEntity cardInfoEntity) {
                        mView.handlerCommit(cardInfoEntity);
                    }

                    @SuppressLint("MissingSuperCall")
                    @Override
                    public void onFailure(int code, String message) {
                        if (code == 2) {
                            mView.handlerCommitFailuer(message);
                        }
                    }
                }));
    }
}
