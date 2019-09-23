package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;

/**
 * @author power
 * @date 2019-09-16 10:51
 * @project BuildTalk
 * @description:
 */
public class DissertationContract {

    interface View extends IView{

        void handlerDetail(DissertationDetailEntity detailEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
