package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.IEntity;

/**
 * @author power
 * @date 2019-11-19 13:50
 * @project BuildTalk
 * @description:
 */
public class ComplaintReasonContract {

    interface View extends IView{

        void handlerComplain(IEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
