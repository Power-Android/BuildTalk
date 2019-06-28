package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/5/28 9:41 AM
 * @project BuildTalk
 * @description:
 */
public class SettingContract {

    interface View extends IView{

        void handlerBind();

        void handlerSuccess(User user);
    }

    interface Presenter extends IPresenter<View>{

    }
}
