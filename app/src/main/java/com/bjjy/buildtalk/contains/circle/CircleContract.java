package com.bjjy.buildtalk.contains.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

import java.util.List;

/**
 * @author power
 * @date 2019/4/26 4:57 PM
 * @project BuildTalk
 * @description:
 */
public class CircleContract {
    interface View extends IView{

        void handlerCircleList(List<String> circle_list);
    }

    interface Presenter extends IPresenter<View>{

    }
}
