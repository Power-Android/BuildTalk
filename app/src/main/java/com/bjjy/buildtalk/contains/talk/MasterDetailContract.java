package com.bjjy.buildtalk.contains.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

import java.util.List;

/**
 * @author power
 * @date 2019/5/14 9:42 AM
 * @project BuildTalk
 * @description:
 */
public class MasterDetailContract {

    interface View extends IView{

        void handlerTab(List<String> list, List<android.view.View> views);
    }

    interface Presenter extends IPresenter<View>{

    }
}
