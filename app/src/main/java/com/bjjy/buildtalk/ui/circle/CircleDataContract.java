package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;

/**
 * @author power
 * @date 2019/6/18 3:59 PM
 * @project BuildTalk
 * @description:
 */
public class CircleDataContract {

    interface View extends IView{

        void handlerSearchCircleInfo(SearchCircleInfoEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
