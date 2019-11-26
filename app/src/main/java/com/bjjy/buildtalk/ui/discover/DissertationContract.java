package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;

import java.util.List;

/**
 * @author power
 * @date 2019-09-16 10:51
 * @project BuildTalk
 * @description:
 */
public class DissertationContract {

    interface View extends IView{

        void handlerDetail(DissertationDetailEntity detailEntity);

        void handlerTabData(List<String> list, List<android.view.View> views);
    }

    interface Presenter extends IPresenter<View>{

    }
}
