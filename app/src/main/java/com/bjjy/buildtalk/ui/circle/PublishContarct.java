package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.IEntity;

/**
 * @author power
 * @date 2019/6/13 9:39 AM
 * @project BuildTalk
 * @description:
 */
public class PublishContarct {

    interface View extends IView{

        void handlerPublishSuccess(IEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
