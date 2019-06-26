package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.entity.SearchResultEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/9 5:11 PM
 * @project BuildTalk
 * @description:
 */
public class TalkSearchContract {

    interface View extends IView{

        void showHistoryData(List<HistoryData> historyDataList);

        void handlerResultSearchList(SearchResultEntity searchResultEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
