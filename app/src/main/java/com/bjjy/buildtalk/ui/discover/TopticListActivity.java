package com.bjjy.buildtalk.ui.discover;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.TopticListAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TopticListActivity extends BaseActivity<TopticListPresenter> implements TopticListContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private int page = 1;
    private List<CourseEntity.CircleInfoBean> mList = new ArrayList<>();
    private int mPage_count = 1;
    private TopticListAdapter mTopticListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toptic_list;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(getString(R.string.top_tic));

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTopticListAdapter = new TopticListAdapter(R.layout.adapter_toptic_list, mList);
        mRecyclerView.setAdapter(mTopticListAdapter);
        mTopticListAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.topticList(page, false);
    }

    @Override
    public void handlerTopticList(CourseEntity courseEntities, boolean isRefresh) {
        mPage_count = courseEntities.getPage_count();
        mList = courseEntities.getCircleInfo();
        if (isRefresh) {
            mTopticListAdapter.setNewData(mList);
        } else {
            mTopticListAdapter.addData(mList);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.topticList(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.topticList(page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
