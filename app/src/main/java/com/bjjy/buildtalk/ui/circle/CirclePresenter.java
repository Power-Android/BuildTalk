package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.entity.CircleEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/26 4:58 PM
 * @project BuildTalk
 * @description:
 */
public class CirclePresenter extends BasePresenter<CircleContract.View> implements CircleContract.Presenter {

    @Inject
    public CirclePresenter() {

    }

    public void circleList(List<CircleEntity> circle_list) {
        circle_list.add(new CircleEntity(""));
        circle_list.add(new CircleEntity(""));
        circle_list.add(new CircleEntity(""));
        circle_list.add(new CircleEntity(""));
        circle_list.add(new CircleEntity(""));
        mView.handlerCircleList(circle_list);
    }
}
