package com.bjjy.buildtalk.base.presenter;

import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.app.DataManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author power
 * @date 2019/4/25 9:08 AM
 * @project BuildTalk
 * @description: Presenter基类
 */
public class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T mView;
    @Inject
    public DataManager mDataManager;
    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
        registerEventBus();
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        unregisterEventBus();
    }

    @Override
    public void reload() {

    }

    @Override
    public void registerEventBus() {
    }

    @Override
    public void unregisterEventBus() {
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
