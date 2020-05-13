package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.base.presenter.IPresenter;

import javax.inject.Inject;

/**
 * @author power
 * @date 2020/5/9 2:17 PM
 * @project BuildTalk
 * @description:
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    @Inject
    public HomePresenter() {

    }
}
