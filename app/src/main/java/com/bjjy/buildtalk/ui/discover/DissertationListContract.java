package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.DissertationListEntity;

/**
 * @author power
 * @date 2019-11-19 09:34
 * @project BuildTalk
 * @description:
 */
public class DissertationListContract {

    interface View extends IView{

        void handlerList(DissertationListEntity dissertationListEntities, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
