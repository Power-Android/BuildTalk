package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleListEntity;
import com.bjjy.buildtalk.entity.CircleMasterEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/10 2:51 PM
 * @project BuildTalk
 * @description:
 */
public class CircleListPresenter extends BasePresenter<CircleListContract.View> implements CircleListContract.Presenter {

    @Inject
    public CircleListPresenter() {
    }

    public void circleList(int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("show", "2");
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(Constants.PAGE, page+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchCircleList(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<CircleListEntity>(mView,false){
                    @Override
                    public void onSuccess(CircleListEntity circleListEntity) {
                        mView.handlerCircleList(circleListEntity, isRefresh);
                    }
                }));
    }

    public void attention(int userId, List<CircleListEntity.CircleInfoBean> mList, int i) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("examine_user", mDataManager.getUser().getUser_id());
        paramas.put(Constants.USER_ID, userId+"");
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
                            mView.handlerAttrntion(baseResponse, mList, i);
                        }
                    }
                }));
    }
}
