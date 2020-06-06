package com.bjjy.buildtalk.ui.video;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.ShortVideoEntity;

/**
 * @author power
 * @date 2020/5/12 2:25 PM
 * @project BuildTalk
 * @description:
 */
public class ShortVideoContract {
    interface View extends IView{

        void handlerVideoSuccess(ShortVideoEntity shortVideoEntity);
    }

    interface Presenter extends IPresenter<View>{

    }

}
