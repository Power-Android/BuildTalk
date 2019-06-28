package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.bjjy.buildtalk.entity.TransactionTabEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/29 9:40 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionContract {

    interface View extends IView{

        void handlerTab(List<TransactionTabEntity> list);

        void handlerList(List<AleadyBuyEntity> list);
    }

    interface Presenter extends IPresenter<View>{

    }
}
