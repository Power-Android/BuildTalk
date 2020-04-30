package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/4/26 4:44 PM
 * @project BuildTalk
 * @description:
 */
public class MineContract {

    interface View extends IView{

        void handlerWallet(String s);

        void handlerUser(User user);
    }

    interface Presenter extends IPresenter<View>{

    }

}
