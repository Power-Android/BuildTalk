package com.bjjy.buildtalk.ui.main;

import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/5/27 10:52 AM
 * @project BuildTalk
 * @description:
 */
public class LoginContract {

    interface View extends IView{

        void handlerBindPhone(String type, String unionid, String openid, String iconurl, String name);

        void handlerSuccess(User user);
    }

    interface Presenter extends IPresenter<View>{

    }
}
