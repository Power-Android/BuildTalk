package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.IEntity;

/**
 * @author power
 * @date 2019/5/28 9:21 AM
 * @project BuildTalk
 * @description:
 */
public class WalletContract  {

    interface View extends IView{

        void handlerWallet(String s);
    }

    interface Presenter extends IPresenter<View>{

    }
}
