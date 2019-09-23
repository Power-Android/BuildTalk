package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/6/13 9:39 AM
 * @project BuildTalk
 * @description:
 */
public class PublishContarct {

    interface View extends IView{

        void handlerPublishSuccess(String iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
