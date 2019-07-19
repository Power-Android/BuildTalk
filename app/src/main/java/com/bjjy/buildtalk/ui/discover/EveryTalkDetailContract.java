package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;

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

        void handlerGuestBookList(GuestBookEntity guestBookEntity);

        void handlerWxOrder(PayOrderEntity payOrderEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
