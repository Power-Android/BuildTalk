package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleListEntity;

/**
 * @author power
 * @date 2019/5/10 2:50 PM
 * @project BuildTalk
 * @description:
 */
public class CircleListContract {

    interface View extends IView{

        void handlerCircleList(CircleListEntity circleListEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
