package com.bjjy.buildtalk.base.presenter;

import com.bjjy.buildtalk.base.view.IView;

/**
 * @author power
 * @date 2019/4/25 9:06 AM
 * @project BuildTalk
 * @description: presenter接口
 */
public interface IPresenter<T extends IView> {
    /**
     * attachView
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * detachView
     */
    void detachView();

    /**
     * 重载方法
     */
    void reload();

    void registerEventBus();

    void unregisterEventBus();
}
