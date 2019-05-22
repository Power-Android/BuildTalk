package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

import java.util.List;

/**
 * @author power
 * @date 2019/5/10 1:58 PM
 * @project BuildTalk
 * @description:
 */
public class MasterListContract {

    interface View extends IView{

        void handlerMasterList(List<String> list);

    }

    interface Presenter extends IPresenter<View>{

    }
}
