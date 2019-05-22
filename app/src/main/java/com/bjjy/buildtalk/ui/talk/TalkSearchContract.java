package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.greendao.HistoryData;

import java.util.List;

/**
 * @author power
 * @date 2019/5/9 5:11 PM
 * @project BuildTalk
 * @description:
 */
public class TalkSearchContract {

    interface View extends IView{

        void handlerResultSearchList(List<String> list);

        void showHistoryData(List<HistoryData> historyDataList);
    }

    interface Presenter extends IPresenter<View>{

    }
}
