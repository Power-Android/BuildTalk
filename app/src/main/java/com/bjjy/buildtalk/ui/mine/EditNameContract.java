package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/6/26 11:13 AM
 * @project BuildTalk
 * @description:
 */
public class EditNameContract {

    interface View extends IView{

        void handlerUpData(String nickName);
    }

    interface Presenter extends IPresenter<View>{

    }
}
