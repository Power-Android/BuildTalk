package com.bjjy.buildtalk.ui.talk;

import android.view.LayoutInflater;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/14 9:43 AM
 * @project BuildTalk
 * @description:
 */
public class MasterDetailPresenter extends BasePresenter<MasterDetailContract.View> implements MasterDetailContract.Presenter {

    private List<String> list = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private View mArticleView;
    private View mIntroductionView;

    @Inject
    public MasterDetailPresenter() {

    }

    public void tabData() {
        list.add(App.getContext().getString(R.string.exquisite_article));
        list.add(App.getContext().getString(R.string.personal_introduction));

        mArticleView = LayoutInflater.from(App.getContext()).inflate(R.layout.master_exquisite_article, null, false);
        mIntroductionView = LayoutInflater.from(App.getContext()).inflate(R.layout.master_personal_introduction, null, false);
        views.add(mArticleView);
        views.add(mIntroductionView);

        mView.handlerTab(list, views);
    }

    public void userDetail(String user_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put("examine_user", mDataManager.getUser().getUser_id());
        }else {
            paramas.put("examine_user", "");
        }
        paramas.put(Constants.USER_ID, user_id);
        paramas.put("user_type", "1");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.userDetail(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<MasterDetailEntity>(mView,false){
                    @Override
                    public void onSuccess(MasterDetailEntity detailEntity) {
                        mView.handlerUserDetail(detailEntity);
                    }
                }));
    }

    public void attention(String user_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("examine_user", mDataManager.getUser().getUser_id());
        paramas.put(Constants.USER_ID, user_id);
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.attention(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView,false){
                    @Override
                    public void onSuccess(IEntity iEntity) {
//                        mView.handlerUserDetail(detailEntity);
                    }

                    @Override
                    public void onNext(BaseResponse<IEntity> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getErrorCode() == 1){
                            mView.handlerAttrntion(baseResponse);
                        }
                    }
                }));
    }
}
