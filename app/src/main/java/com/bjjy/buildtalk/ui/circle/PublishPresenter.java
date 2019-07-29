package com.bjjy.buildtalk.ui.circle;

import android.text.TextUtils;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * @author power
 * @date 2019/6/13 9:40 AM
 * @project BuildTalk
 * @description:
 */
public class PublishPresenter extends BasePresenter<PublishContarct.View> {

    @Inject
    public PublishPresenter() {

    }

    public void publishTheme(String circle_id, int theme_id, String theme_content, boolean isEdit, List<ThemeImageBean> list) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPart() != null){
                parts.add(list.get(i).getPart());
            }
        }
        if (parts.size() > 0){
            String timestamp = String.valueOf(TimeUtils.getNowSeconds());
            Map<String, String> paramas = new HashMap<>();
            paramas.put(Constants.TIMESTAMP, timestamp);
            String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

            Map<String, String> headers = new HashMap<>();
            headers.put(Constants.TIMESTAMP, timestamp);
            headers.put(Constants.SIGN, sign);

            addSubscribe(mDataManager.uploadFiles(parts)
                    .compose(RxUtils.SchedulerTransformer())
                    .filter(stringBaseResponse -> mView != null)
                    .subscribeWith(new BaseObserver<String>(mView, false) {
                        @Override
                        public void onSuccess(String picUrl) {
                            publish(circle_id, theme_id, theme_content, picUrl, isEdit, list);
                        }
                    }));
        }else {
            publish(circle_id, theme_id, theme_content, null, isEdit, list);
        }
    }

    private void publish(String circle_id, int theme_id, String theme_content, String picUrl, boolean isEdit, List<ThemeImageBean> list) {
        if (!isEdit){
            newTheme(circle_id, theme_content, picUrl);
        }else {
            editTheme(circle_id, theme_id, theme_content, picUrl, list);
        }
    }

    private void newTheme(String circle_id, String theme_content, String picUrl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("circle_id", circle_id);
        paramas.put("theme_content", theme_content);
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.publishTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(baseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerPublishSuccess(iEntity);
                    }
                }));
    }

    private void editTheme(String circle_id, int theme_id, String theme_content, String picUrl, List<ThemeImageBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPic_url().contains("https") && !TextUtils.isEmpty(picUrl)){
                picUrl = list.get(i).getPic_url() + "," + picUrl;
            }else if (list.get(i).getPic_url().contains("https") && TextUtils.isEmpty(picUrl)){
                picUrl = StringUtils.listToString2(list, ',');
            }
        }
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", theme_id+"");
        paramas.put("theme_content", theme_content);
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.updateTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(baseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerPublishSuccess(iEntity);
                    }
                }));
    }
}
