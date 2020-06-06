package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2020/5/29 11:00 AM
 * @project BuildTalk
 * @description:
 */
public class VideoPublishContract {
    interface View extends IView{

        void handlerSignSuccess(String sign);
    }
    interface Presenter extends IPresenter<View>{

    }
}
