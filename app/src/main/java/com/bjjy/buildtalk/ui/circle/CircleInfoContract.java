package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleMasterInfoEntity;
import com.bjjy.buildtalk.entity.IEntity;

/**
 * @author power
 * @date 2019/6/12 9:32 AM
 * @project BuildTalk
 * @description:
 */
public class CircleInfoContract {

    interface View extends IView{

        void handlerMasterInfo(CircleMasterInfoEntity masterInfoEntity);

        void handlerQuitCircle(IEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
