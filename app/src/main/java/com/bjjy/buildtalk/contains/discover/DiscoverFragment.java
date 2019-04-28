package com.bjjy.buildtalk.contains.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DiscoverAdapter;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author power
 * @date 2019/4/26 4:34 PM
 * @project BuildTalk
 * @description: 发现 模块
 */
public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View, OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.discover_recyclerView)
    RecyclerView mDiscoverRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.normalView)
    LinearLayout mNormalView;

    private List<DiscoverEntity> discoverEntityList = new ArrayList<>();
    private DiscoverAdapter mDiscoverAdapter;

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        mToolbarTitle.setText(R.string.discover);
        mRefreshLayout.setOnRefreshListener(this);
        mDiscoverRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDiscoverAdapter = new DiscoverAdapter(discoverEntityList);
        mDiscoverRecyclerView.setAdapter(mDiscoverAdapter);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.discoverType(discoverEntityList);
    }

    @Override
    public void handlerDiscoverType(List<DiscoverEntity> discoverEntityList) {
        mDiscoverAdapter.setNewData(discoverEntityList);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000);
    }
}
