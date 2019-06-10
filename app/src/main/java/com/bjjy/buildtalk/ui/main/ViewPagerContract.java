package com.bjjy.buildtalk.ui.main;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/6/10 9:50 AM
 * @project BuildTalk
 * @description:
 */
public class ViewPagerContract {

    interface View extends IView{

    }

    interface Presenter extends IPresenter<View>{

    }
}
