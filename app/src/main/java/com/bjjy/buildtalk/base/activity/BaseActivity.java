package com.bjjy.buildtalk.base.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.ui.main.MainActivity;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.player.PlayerWindowManager;

import java.util.List;

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
        implements IView, HasSupportFragmentInjector, View.OnTouchListener {
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    @Inject
    protected T mPresenter;
    @Inject
    DataManager mDataManager;
    private ViewGroup mNormalView;
    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private View mNoNetView;
    private int mCurrentState = NORMAL_STATE;
    private boolean isLoading = true;//是否使用状态布局
    private BaseDialog mLoadingDialog;
    private Uri data;
    private PlayerWindowManager mPlayerWindowManager;
    private boolean isMargin;
    private GestureDetector mGestureDetector;

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

        initLoadingDialog();
        mPlayerWindowManager = PlayerWindowManager.getInstance();
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (mPlayerWindowManager.getBinder().isPlaying() && mPlayerWindowManager.getPlayerView() != null){
                    if (velocityY >= 0) {
//                    TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                            0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
                        mPlayerWindowManager.getPlayerView().setVisibility(View.VISIBLE);
//                    mHiddenAction.setDuration(1000);
//                    PlayerWindowManager.getInstance().getPlayerView().startAnimation(mHiddenAction);

                    } else {
//                    TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                            1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        mPlayerWindowManager.getPlayerView().setVisibility(View.GONE);
//                    mHiddenAction.setDuration(1000);
//                    PlayerWindowManager.getInstance().getPlayerView().startAnimation(mHiddenAction);

                    }
                }

                return false;
            }
        });
        mNormalView = findViewById(R.id.normalView);
        if (mNormalView == null) {
            isLoading = !isLoading;
            return;
        }

        /**
         * 初始化所有state_view
         */
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.error_view, parent);
        View.inflate(this, R.layout.no_net_view, parent);
        mEmptyView = findViewById(R.id.emptyView);
        mErrorView = parent.findViewById(R.id.errorView);
        mNoNetView = parent.findViewById(R.id.noNetView);

        mErrorView.setOnClickListener(v -> recreate());
        View mErrorViewincludeToolbar = mErrorView.findViewById(R.id.include_toolbar);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(mErrorViewincludeToolbar.getLayoutParams());
        lp1.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        mErrorViewincludeToolbar.setLayoutParams(lp1);
        mErrorViewincludeToolbar.findViewById(R.id.toolbar_left_back).setOnClickListener(v -> finish());

        mNoNetView.findViewById(R.id.tv_reload).setOnClickListener(v -> recreate());

        //初始化View状态
        mNormalView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mNoNetView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDataManager.getIsShowPlayer()){
            showPlayer(mPlayerWindowManager.getSongId());
        }
    }

    public PlayerWindowManager getPlayerWindowManager(){
        return mPlayerWindowManager;
    }

    @Override
    public void showPlayer(String songId) {
        mDataManager.setIsSHowPlayer(true);
        if (!TextUtils.isEmpty(songId)) {
            LogUtils.e(isMargin);
            mPlayerWindowManager.showFloatPlayer(this, mDataManager, songId, isMargin);
        }
    }

    /**
     * 改变播放器高度
     */
    public void setIsMargin(boolean isMargin){
        this.isMargin = isMargin;
    }

    @Override
    public void hidePlayer() {
        //TODO 停止播放，调用stop方法
        mDataManager.setIsSHowPlayer(false);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void showLoading() {
        if (!isLoading) return;
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (!isLoading) return;
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        if (!isLoading) return;
        if (mCurrentState == ERROR_STATE) return;
        hideCurrentViewByState();
        mCurrentState = ERROR_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showNoNetwork() {
        if (!isLoading) return;
        if (mCurrentState == NONET_STATE) return;
        hideCurrentViewByState();
        mCurrentState = NONET_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showEmpty() {
        if (!isLoading) return;
        if (mCurrentState == EMPTY_STATE) return;
        mCurrentState = EMPTY_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showNormal() {
        if (!isLoading) return;
        if (mCurrentState == NORMAL_STATE) return;
        hideCurrentViewByState();
        mCurrentState = NORMAL_STATE;
        showCurrentViewByState();
    }

    private void initLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new BaseDialog.Builder(this)
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
        if (showView == null) return;
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
        if (hideView == null) return;
        AnimatorUtils.hideByAlpha(hideView);
    }

    public boolean isExistMainActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity    
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) {// 说明它已经启动了
                    flag = true;
                    break;//跳出循环，优化效率
                }
            }
        }
        return flag;

    }
}
