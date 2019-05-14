package com.bjjy.buildtalk.contains.talk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.SearchResultAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListActivity extends BaseActivity<MasterListPresenter> implements MasterListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<String> mList = new ArrayList<>();
    private SearchResultAdapter mMasterListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_list;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.industry_higher_ups);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMasterListAdapter = new SearchResultAdapter(R.layout.adapter_search_result, mList);
        mRecyclerView.setAdapter(mMasterListAdapter);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.masterList();
    }

    @Override
    public void handlerMasterList(List<String> list) {
        mMasterListAdapter.setNewData(list);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
    }
}
