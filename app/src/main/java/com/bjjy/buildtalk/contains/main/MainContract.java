package com.bjjy.buildtalk.contains.main;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/4/25 3:27 PM
 * @project BuildTalk
 * @description:
 */
public class MainContract {
    interface View extends IView {
        void handleSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void test();
    }
}
