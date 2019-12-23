package com.bjjy.buildtalk.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.player.PlayerWindowManager;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * @author power
 * @date 2019/4/25 11:24 AM
 * @project BuildTalk
 * @description: BaseFragment
 */
public abstract class BaseFragment<T extends IPresenter> extends AbstractFragment implements IView {

    @Inject
    protected T mPresenter;
    @Inject
    DataManager mDataManager;
    protected Context mContext;
    private BaseDialog mLoadingDialog;
    private PlayerWindowManager mPlayerWindowManager;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        this.mContext = context;
        mPlayerWindowManager = PlayerWindowManager.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initLoadingDialog();
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
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    private void initLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new BaseDialog.Builder(mContext)
                    .setViewId(R.layout.loading_view)
                    .setGravity(Gravity.CENTER)
                    .setStyle(R.style.dialog_style1)
                    .setAnimation(R.style.nomal_aniamtion)
                    .setWidthHeightdp(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .isOnTouchCanceled(false)
                    .builder();
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        hideLoading();
        super.onDestroy();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showNoNetwork() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showPlayer(String songId) {
        mDataManager.setIsSHowPlayer(true);
        if (!TextUtils.isEmpty(songId)) {
            mPlayerWindowManager.showFloatPlayer(getActivity(), mDataManager, songId, true);
        }
    }

    @Override
    public void hidePlayer() {

    }

}
