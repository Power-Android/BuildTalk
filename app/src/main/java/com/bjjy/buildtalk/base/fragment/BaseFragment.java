package com.bjjy.buildtalk.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.bjjy.buildtalk.app.Constants.ERROR_STATE;
import static com.bjjy.buildtalk.app.Constants.LOADING_STATE;
import static com.bjjy.buildtalk.app.Constants.NORMAL_STATE;

/**
 * @author power
 * @date 2019/4/25 11:24 AM
 * @project BuildTalk
 * @description: BaseFragment 默认使用状态布局，根布局的id必须为normalView，且必须是Viewgroup
 */
public abstract class BaseFragment<T extends IPresenter> extends AbstractFragment implements IView {

    @Inject
    protected T mPresenter;

    protected Context mContext;
    private ViewGroup mNormalView;
    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private int mCurrentState = NORMAL_STATE;
    private boolean isLoading = true;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        if(getView() == null) return;
        mNormalView = getView().findViewById(R.id.normalView);

        if (mNormalView == null){
            isLoading = !isLoading;
            return;
        }

        /**
         * 抛出异常后，如果没有try/catch不会继续执行
         * 初始化所有state_view
         */
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(mContext, R.layout.error_view, parent);
        View.inflate(mContext, R.layout.loaging_view, parent);
        View.inflate(mContext,R.layout.empty_view,parent);
        mErrorView = parent.findViewById(R.id.errorView);
        mLoadingView = parent.findViewById(R.id.loadingView);
        mEmptyView = parent.findViewById(R.id.emptyView);
        mErrorView.setOnClickListener(v -> mPresenter.reload());
        mEmptyView.setOnClickListener(v -> mPresenter.reload());

        //初始化View状态
        mNormalView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        hideLoading();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter = null;
        }
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
