package com.bjjy.buildtalk.ui.main;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/5/27 11:16 AM
 * @project BuildTalk
 * @description:
 */
public class PhoneLoginContract {
    interface View extends IView {

        void handlerLoginSuccess(User user);
    }

    interface Presenter extends IPresenter<View> {

    }
}
