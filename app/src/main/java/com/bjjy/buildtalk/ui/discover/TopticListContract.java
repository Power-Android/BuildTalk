package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CourseEntity;

/**
 * @author power
 * @date 2019/5/6 5:47 PM
 * @project BuildTalk
 * @description:
 */
public class TopticListContract {
    interface View extends IView{

        void handlerTopticList(CourseEntity courseEntities, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
