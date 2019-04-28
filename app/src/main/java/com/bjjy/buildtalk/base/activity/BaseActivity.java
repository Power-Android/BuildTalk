package com.bjjy.buildtalk.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.bjjy.buildtalk.app.Constants.ERROR_STATE;
import static com.bjjy.buildtalk.app.Constants.LOADING_STATE;
import static com.bjjy.buildtalk.app.Constants.NORMAL_STATE;

/**
 * @author power
 * @date 2019/4/25 9:14 AM
 * @project BuildTalk
 * @description: BaseActivity 默认使用状态布局，根布局的id必须为normalView，且必须是Viewgroup
 */
public abstract class BaseActivity<T extends IPresenter> extends AbstractActivity
        implements IView, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    @Inject
    protected T mPresenter;
    private ViewGroup mNormalView;
    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private TextView mTvReload;
    private int mCurrentState = NORMAL_STATE;
    private boolean isLoading = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        mNormalView = findViewById(R.id.normalView);
        if (mNormalView == null){
            isLoading = !isLoading;
            return;
        }

        /**
         * 初始化所有state_view
         */
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.error_view, parent);
        View.inflate(this, R.layout.loaging_view, parent);
        View.inflate(this,R.layout.empty_view,parent);
        mErrorView = parent.findViewById(R.id.errorView);
        mLoadingView = parent.findViewById(R.id.loadingView);
        mEmptyView = parent.findViewById(R.id.emptyView);

        mTvReload = mErrorView.findViewById(R.id.tv_reload);
        mErrorView.setOnClickListener(v -> mPresenter.reload());
        TextView mTvRefresh = mEmptyView.findViewById(R.id.tv_refresh);
        mTvRefresh.setOnClickListener(v -> mPresenter.reload());

        //初始化View状态
        mNormalView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        hideLoading();
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        KeyboardUtils.hideSoftInput(this.getWindow().getDecorView().getRootView());
        super.onBackPressed();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void showLoading() {
        if (!isLoading) return;
        if(mCurrentState == LOADING_STATE) return;
        hideCurrentViewByState();
        mCurrentState = LOADING_STATE;
        showCurrentViewByState();
    }

    @Override
    public void hideLoading() {
        if (!isLoading) return;
    }

    @Override
    public void showError() {
        if (!isLoading) return;
        if(mCurrentState == ERROR_STATE) return;
        hideCurrentViewByState();
        mCurrentState = ERROR_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showNoNetwork() {
        if (!isLoading) return;
    }

    @Override
    public void showEmpty() {
        if (!isLoading) return;
    }

    @Override
    public void showNormal() {
        if (!isLoading) return;
        if(mCurrentState == NORMAL_STATE) return;
        hideCurrentViewByState();
        mCurrentState = NORMAL_STATE;
        showCurrentViewByState();
    }

    /**
     * 显示当前布局根据mCurrentState
     */
    private void showCurrentViewByState() {
        View showView = null;
        switch (mCurrentState) {
            case NORMAL_STATE:
                showView = mNormalView;
                break;
            case LOADING_STATE:
                showView = mLoadingView;
                break;
            case ERROR_STATE:
                showView = mErrorView;
            default:
                break;
        }
        if(showView == null) return;
        AnimatorUtils.showByAlpha(showView);
    }


    /**
     * 隐藏当前布局根据mCurrentState
     */
    private void hideCurrentViewByState() {
        View hideView = null;
        switch (mCurrentState) {
            case NORMAL_STATE:
                hideView = mNormalView;
                break;
            case LOADING_STATE:
                hideView = mLoadingView;
                break;
            case ERROR_STATE:
                hideView = mErrorView;
            default:
                break;
        }
        if(hideView == null) return;
        AnimatorUtils.hideByAlpha(hideView);
    }
}
