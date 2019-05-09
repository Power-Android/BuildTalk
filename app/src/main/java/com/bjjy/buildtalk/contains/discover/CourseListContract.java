package com.bjjy.buildtalk.contains.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CourseEntity;

/**
 * @author power
 * @date 2019/5/6 5:52 PM
 * @project BuildTalk
 * @description:
 */
public class CourseListContract {

    interface View extends IView{

        void handlerCourseList(CourseEntity courseEntities, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
