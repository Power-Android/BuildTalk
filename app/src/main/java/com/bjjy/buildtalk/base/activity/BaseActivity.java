package com.bjjy.buildtalk.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.bjjy.buildtalk.app.Constants.EMPTY_STATE;
import static com.bjjy.buildtalk.app.Constants.ERROR_STATE;
import static com.bjjy.buildtalk.app.Constants.LOADING_STATE;
import static com.bjjy.buildtalk.app.Constants.NONET_STATE;
import static com.bjjy.buildtalk.app.Constants.NORMAL_STATE;

/**
 * @author power
 * @date 2019/4/25 9:14 AM
 * @project BuildTalk
 * @description: BaseActivity 使用状态布局，根布局的id必须为normalView，且必须是Viewgroup
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
    private View mNoNetView;
    private int mCurrentState = NORMAL_STATE;
    private boolean isLoading = true;//是否使用状态布局

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
        View.inflate(this, R.layout.no_net_view, parent);
        mEmptyView = findViewById(R.id.emptyView);
        mErrorView = parent.findViewById(R.id.errorView);
        mLoadingView = parent.findViewById(R.id.loadingView);
        mNoNetView = parent.findViewById(R.id.noNetView);

        mErrorView.setOnClickListener(v -> recreate());
        View mErrorViewincludeToolbar = mErrorView.findViewById(R.id.include_toolbar);
        Toolbar mErrorViewtoolbar = mErrorView.findViewById(R.id.toolbar);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(mErrorViewincludeToolbar.getLayoutParams());
        lp1.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        mErrorViewincludeToolbar.setLayoutParams(lp1);
        mErrorViewtoolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mErrorViewtoolbar.setNavigationOnClickListener(v -> finish());

        View includeToolbar = mNoNetView.findViewById(R.id.include_toolbar);
        Toolbar toolbar = mNoNetView.findViewById(R.id.toolbar);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(includeToolbar.getLayoutParams());
        lp.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        includeToolbar.setLayoutParams(lp);
        toolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        toolbar.setNavigationOnClickListener(v -> finish());
        mNoNetView.findViewById(R.id.tv_reload).setOnClickListener(v -> recreate());

        //初始化View状态
        mNormalView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        mNoNetView.setVisibility(View.INVISIBLE);
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
        mCurrentState = LOADING_STATE;
        showCurrentViewByState();
    }

    @Override
    public void hideLoading() {
        if (!isLoading) return;
        if(mCurrentState == LOADING_STATE){
            mNormalView.setFocusable(true);
            hideCurrentViewByState();
        }
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
        if(mCurrentState == NONET_STATE) return;
        hideCurrentViewByState();
        mCurrentState = NONET_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showEmpty() {
        if (!isLoading) return;
        if(mCurrentState == EMPTY_STATE) return;
        mCurrentState = EMPTY_STATE;
        showCurrentViewByState();
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
                break;
            case EMPTY_STATE:
                showView = mEmptyView;
                break;
            case NONET_STATE:
                showView = mNoNetView;
                break;
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
                break;
            case EMPTY_STATE:
                hideView = mEmptyView;
                break;
            case NONET_STATE:
                hideView = mNoNetView;
                break;
            default:
                break;
        }
        if(hideView == null) return;
        AnimatorUtils.hideByAlpha(hideView);
    }
}
