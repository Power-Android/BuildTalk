package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.DisrOrAttenEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2020/5/9 2:17 PM
 * @project BuildTalk
 * @description:
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    @Inject
    public HomePresenter() {

    }

    public void discover(int page) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put("type_id", "2");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.searchFindTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<DisrOrAttenEntity>(mView, false) {
                    @Override
                    public void onSuccess(DisrOrAttenEntity disrOrAttenEntity) {
                        mView.handlerDiscover(disrOrAttenEntity);
                    }
                }));
    }

    public void attention(int page) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put("type_id", "1");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.searchFindTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<DisrOrAttenEntity>(mView, false) {
                    @Override
                    public void onSuccess(DisrOrAttenEntity disrOrAttenEntity) {
                        mView.handlerAttention(disrOrAttenEntity);
                    }
                }));
    }

    public void getThumb(String pic_url, List<DisrOrAttenEntity.ThemeInfoBean> data, int i, boolean isEdit) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("pic_url", pic_url);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.getThumb(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, true, true) {
                    @Override
                    public void onSuccess(String thumb_url) {
                        mView.handlerThumbSuccess(thumb_url, data, i, isEdit);
                    }
                }));
    }
}
