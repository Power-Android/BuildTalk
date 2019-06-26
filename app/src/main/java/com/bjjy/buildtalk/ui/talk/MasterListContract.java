package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.MasterListEntity;

/**
 * @author power
 * @date 2019/5/10 1:58 PM
 * @project BuildTalk
 * @description:
 */
public class MasterListContract {

    interface View extends IView{

        void handlerMasterList(MasterListEntity masterListEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
