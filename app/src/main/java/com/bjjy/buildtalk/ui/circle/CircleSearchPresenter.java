package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author power
 * @date 2019/5/15 9:31 AM
 * @project BuildTalk
 * @description:
 */
public class CircleSearchPresenter extends BasePresenter<CircleSearchContract.View> implements CircleSearchContract.Presenyer {


    @Inject
    public CircleSearchPresenter() {
    }

    public void getSearchData() {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<CircleHistoryData>>) e -> {
            List<CircleHistoryData> historyDataList = mDataManager.loadAllCircleHistoryData();
            e.onNext(historyDataList);
        }).compose(RxUtils.SchedulerTransformer())
                .filter(articleListData -> mView != null)
                .subscribe(historyDataList -> {
                            Collections.reverse(historyDataList);
                            mView.showHistoryData(historyDataList);
                        }
                ));
    }

    public void addHistoryData(int page, String content, boolean isRefresh) {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<CircleHistoryData>>) e -> {
            List<CircleHistoryData> historyDataList = mDataManager.addCircleHistoryData(content);
            e.onNext(historyDataList);
        }).compose(RxUtils.SchedulerTransformer())
                .filter(articleListData -> mView != null)
                .subscribe(historyDataList -> {
                            searchResultList(page, content, isRefresh);
                        }
                ));
    }

    private void searchResultList(int page, String content, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("searchKeyword", content);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.searchHistory(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<SearchResultEntity>(mView, true) {
                    @Override
                    public void onSuccess(SearchResultEntity searchResultEntity) {
                        mView.handlerResultSearchList(searchResultEntity, isRefresh);
                    }
                }));
    }
}
