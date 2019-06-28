package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/6/11 1:54 PM
 * @project BuildTalk
 * @description:
 */
public class SetPictureContract {

    interface View extends IView{

        void handlerUpData(String picUrl);
    }

    interface Presenter extends IPresenter<View>{

    }
}
