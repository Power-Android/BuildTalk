package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;

/**
 * @author power
 * @date 2019/5/5 1:40 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkContract {
    interface View extends IView{

        void handlerTalkList(EveryTalkListEntity everyTalkListEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

        void talkList(int page, boolean isRefresh);
    }
}
