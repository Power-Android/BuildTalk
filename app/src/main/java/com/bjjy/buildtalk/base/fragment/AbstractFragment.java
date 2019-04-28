package com.bjjy.buildtalk.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author power
 * @date 2019/4/25 11:14 AM
 * @project BuildTalk
 * @description:
 */
public abstract class AbstractFragment extends Fragment {
    private String TAG = "AbstractFragment";
    private Unbinder unBinder;
    private boolean isViewCreated = false;//布局是否被创建
    private boolean isLoadData = false;//数据是否加载
    private boolean isFirstVisible = true;//是否第一次可见

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isFragmentVisible(this) && this.isAdded()){
            if (this.getParentFragment() == null || isFragmentVisible(this.getParentFragment())) {
                initEventAndData();
                isLoadData = true;
                if(isFirstVisible)
                    isFirstVisible = false;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint(): "
                + " hide: " + this.isHidden()
                + " add :" + this.isAdded()
                + " visible: " + this.isVisible()
                + " resumed: " + this.isResumed());
        if(isFragmentVisible(this) && !isLoadData && isViewCreated && this.isAdded()){
            initEventAndData();
            isLoadData = true;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged(): "
                + " hide: " + this.isHidden()
                + " add :" + this.isAdded()
                + " visible: " + this.isVisible()
                + " resumed: " + this.isResumed());
        //onHiddenChanged调用在Resumed之前，所以此时可能fragment被add, 但还没resumed
        if(!hidden && !this.isResumed())
            return;
        //使用hide和show时，fragment的所有生命周期方法都不会调用，除了onHiddenChanged（）
        if(!hidden && isFirstVisible && this.isAdded()){
            initEventAndData();
            isFirstVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null && unBinder != Unbinder.EMPTY) {
            unBinder.unbind();
            unBinder = null;
        }
        isViewCreated = false;
        isLoadData = false;
        isFirstVisible = true;
    }

    /**
     * 当前Fragment是否可见
     */
    private boolean isFragmentVisible(Fragment fragment) {
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 有些初始化必须在onCreateView中，例如setAdapter,
     * 否则，会弹出 No adapter attached; skipping layout
     */
    protected abstract void initView();

    /**
     * 初始化数据-懒加载
     */
    protected abstract void initEventAndData();
}
