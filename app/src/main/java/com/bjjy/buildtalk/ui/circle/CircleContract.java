package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/4/26 4:57 PM
 * @project BuildTalk
 * @description:
 */
public class CircleContract {
    interface View extends IView{

        void handlerCircleList(List<CircleEntity> circle_list);
    }

    interface Presenter extends IPresenter<View>{

    }
}
