package com.bjjy.buildtalk.contains.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;

/**
 * @author power
 * @date 2019/5/7 1:34 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkDetailContract {

    interface View extends IView{

        void handlerTalkDetail(EveryTalkDetailEntity everyTalkDetailEntity);

        void handlerSaveRecord();

        void praiseSuccess(boolean isSuccess, int position, boolean isPraise);

        void collectSuccess(boolean isSuccess, boolean isCollect);
    }

    interface Presenter extends IPresenter<View>{

    }
}
