package com.bjjy.buildtalk.ui.circle;

import android.view.View;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

import java.util.List;

/**
 * @author power
 * @date 2019/5/24 10:29 AM
 * @project BuildTalk
 * @description:
 */
public class CourseCircleContract {

    interface View extends IView{

        void handlerTab(List<String> titleList, List<android.view.View> views, List<Integer> badgeCountList);
    }

    interface Presenyer extends IPresenter<View>{

    }
}
