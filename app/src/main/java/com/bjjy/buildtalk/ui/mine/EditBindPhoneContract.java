package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/6/27 10:19 AM
 * @project BuildTalk
 * @description:
 */
public class EditBindPhoneContract {

    interface View extends IView{

        void handlerUpdate();

        void handlerBindPhone();

    }

    interface Presenter extends IPresenter<View>{

    }
}
