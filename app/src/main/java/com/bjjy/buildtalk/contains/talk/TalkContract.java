package com.bjjy.buildtalk.contains.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/4/26 4:36 PM
 * @project BuildTalk
 * @description:
 */
public class TalkContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {

    }
}
