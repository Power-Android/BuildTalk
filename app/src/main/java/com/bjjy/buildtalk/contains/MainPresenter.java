package com.bjjy.buildtalk.contains;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/25 4:20 PM
 * @project BuildTalk
 * @description:
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void test() {
        addSubscribe(mDataManager.getArticleList(1)
                .compose(RxUtils.SchedulerTransformer())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        App.getContext().getString(R.string.http_error),
                        true) {
                    @Override
                    public void onSuccess(ArticleListData loginData) {
                        mView.handleSuccess();
                    }
                }));
    }
}
