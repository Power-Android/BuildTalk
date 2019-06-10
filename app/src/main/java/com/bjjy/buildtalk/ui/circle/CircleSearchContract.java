package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.entity.SearchResultEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/15 9:30 AM
 * @project BuildTalk
 * @description:
 */
public class CircleSearchContract {

    interface View extends IView{

        void showHistoryData(List<CircleHistoryData> historyDataList);

        void handlerResultSearchList(SearchResultEntity searchResultEntity, boolean isRefresh);
    }

    interface Presenyer extends IPresenter<View>{

    }
}
