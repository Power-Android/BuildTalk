package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CollectEntity;

/**
 * @author power
 * @date 2019/5/14 4:47 PM
 * @project BuildTalk
 * @description:
 */
public class MasterCollectContract {

    interface View extends IView{

        void handlerCollect(CollectEntity collectEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
