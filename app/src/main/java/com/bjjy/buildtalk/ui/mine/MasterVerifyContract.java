package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CardInfoEntity;

/**
 * @author power
 * @date 2019-08-20 09:29
 * @project BuildTalk
 * @description:
 */
public class MasterVerifyContract {

    interface View extends IView{

        void handlerCardInfo(CardInfoEntity cardInfoEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
