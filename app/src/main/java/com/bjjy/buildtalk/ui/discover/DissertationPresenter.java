package com.bjjy.buildtalk.ui.discover;

import android.view.LayoutInflater;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019-09-16 10:52
 * @project BuildTalk
 * @description:
 */
public class DissertationPresenter extends BasePresenter<DissertationContract.View> {

    private List<String> list = new ArrayList<>();
    private List<View> views = new ArrayList<>();

    @Inject
    public DissertationPresenter() {

    }

    public void tabData() {
        list.add("详情");
        list.add("视频");
        View detailView = LayoutInflater.from(App.getContext()).inflate(R.layout.dissertation_detail_layout, null, false);
        View videoView = LayoutInflater.from(App.getContext()).inflate(R.layout.dissertation_video_layout, null, false);
        views.add(detailView);
        views.add(videoView);

        mView.handlerTabData(list, views);
    }

    public void dissertation(String id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("dissertation_id", id);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchDissertationDetail(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<DissertationDetailEntity>(mView, false){
                    @Override
                    public void onSuccess(DissertationDetailEntity detailEntity) {
                        mView.handlerDetail(detailEntity);
                    }
                }));
    }


}
