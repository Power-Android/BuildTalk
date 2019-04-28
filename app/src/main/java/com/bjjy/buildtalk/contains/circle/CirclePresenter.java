package com.bjjy.buildtalk.contains.circle;

import com.bjjy.buildtalk.base.presenter.BasePresenter;

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

    public void circleList(List<String> circle_list) {
        circle_list.add("");
        circle_list.add("");
        circle_list.add("");
        circle_list.add("");
        mView.handlerCircleList(circle_list);
    }
}
