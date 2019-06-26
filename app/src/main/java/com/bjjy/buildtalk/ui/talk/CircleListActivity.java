package com.bjjy.buildtalk.ui.talk;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleListAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.CircleListEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CircleListActivity extends BaseActivity<CircleListPresenter> implements CircleListContract.View, OnRefreshLoadMoreListener {

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

    private int page = 1;
    private int mPage_count = 1;
    private List<CircleListEntity.CircleInfoBean> mCircleInfo = new ArrayList<>();
    private CircleListAdapter mCircleListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_list;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText("人气圈主排行");

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCircleListAdapter = new CircleListAdapter(R.layout.adapter_circle_list,mCircleInfo);
        mRecyclerView.setAdapter(mCircleListAdapter);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleList(page, false);
    }

    @Override
    public void handlerCircleList(CircleListEntity circleListEntity, boolean isRefresh) {
        mPage_count = circleListEntity.getPage_count();
        mCircleInfo = circleListEntity.getCircleInfo();
        if (isRefresh){
            mCircleListAdapter.setNewData(mCircleInfo);
        }else {
            mCircleListAdapter.addData(mCircleInfo);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.circleList(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.circleList(page, true);
        refreshLayout.finishRefresh();
    }
}
