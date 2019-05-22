package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.core.rx.RxUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void addHistoryData(String content) {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<CircleHistoryData>>) e -> {
            List<CircleHistoryData> historyDataList = mDataManager.addCircleHistoryData(content);
            e.onNext(historyDataList);
        }).compose(RxUtils.SchedulerTransformer())
                .filter(articleListData -> mView != null)
                .subscribe(historyDataList -> {
                            searchResultList(content);
                        }
                ));
    }

    private void searchResultList(String content) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        List<CircleHistoryData> historyDataList = mDataManager.loadAllCircleHistoryData();
        Collections.reverse(historyDataList);
        mView.showHistoryData(historyDataList);
        mView.handlerResultSearchList(list);
    }
}
