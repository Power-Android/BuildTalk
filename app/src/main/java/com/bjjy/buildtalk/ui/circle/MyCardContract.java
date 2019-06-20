package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.MyCardEntity;

/**
 * @author power
 * @date 2019/6/12 1:56 PM
 * @project BuildTalk
 * @description:
 */
public class MyCardContract {

    interface View extends IView{

        void handlerMyCard(MyCardEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
