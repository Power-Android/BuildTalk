package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.core.rx.RxUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author power
 * @date 2019/5/9 5:12 PM
 * @project BuildTalk
 * @description:
 */
public class TalkSearchPresenter extends BasePresenter<TalkSearchContract.View> implements TalkSearchContract.Presenter {
    @Inject
    public TalkSearchPresenter() {
    }

    public void getSearchData() {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<HistoryData>>) e -> {
            List<HistoryData> historyDataList = mDataManager.loadAllHistoryData();
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
        addSubscribe(Observable.create((ObservableOnSubscribe<List<HistoryData>>) e -> {
            List<HistoryData> historyDataList = mDataManager.addHistoryData(content);
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
        List<HistoryData> historyDataList = mDataManager.loadAllHistoryData();
        Collections.reverse(historyDataList);
        mView.showHistoryData(historyDataList);
        mView.handlerResultSearchList(list);
    }


}
