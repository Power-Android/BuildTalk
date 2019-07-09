package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * @author power
 * @date 2019/5/16 4:59 PM
 * @project BuildTalk
 * @description:
 */
public class CreateCirclePresenter extends BasePresenter<CreateCircleContract.View> implements CreateCircleContract.Presenter{

    @Inject
    public CreateCirclePresenter() {

    }

    public void circleTags() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.searchCircleTags(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(circleTagEntity -> mView != null)
                .subscribeWith(new BaseObserver<List<CircleTagEntity>>(mView, false) {
                    @Override
                    public void onSuccess(List<CircleTagEntity> circleTagEntityList) {
                        mView.handlerTagList(circleTagEntityList);
                    }
                }));
    }

    public void upload(MultipartBody.Part file, String circle_name, String circle_tags, String circle_desc) {
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
                        creatCircle(picUrl,circle_name,circle_tags,circle_desc);
                    }
                }));
    }

    private void creatCircle(String pic_url, String circle_name, String circle_tags, String circle_desc) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("circle_image", pic_url);
        paramas.put("circle_name", circle_name);
        paramas.put("circle_tags", circle_tags);
        paramas.put("circle_desc", circle_desc);
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.createCircle(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(iEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, false) {
                    @Override
                    public void onSuccess(String iEntity) {
                        mView.handlerCreateSuccess(iEntity);
                    }
                }));
    }

    public void searchCircleInfo(String circle_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("circle_id", circle_id);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.searchCircleInfo(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<SearchCircleInfoEntity>(mView, false) {
                    @Override
                    public void onSuccess(SearchCircleInfoEntity infoEntity) {
                        mView.handlerSearchCircleInfo(infoEntity);
                    }
                }));
    }
}
