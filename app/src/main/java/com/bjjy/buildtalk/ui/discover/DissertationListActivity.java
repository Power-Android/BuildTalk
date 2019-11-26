package com.bjjy.buildtalk.ui.discover;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DissertationListAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.DissertationListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DissertationListActivity extends BaseActivity<DissertationListPresenter> implements DissertationListContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<DissertationListEntity.DissertationInfoBean> mList = new ArrayList<>();
    private int page = 1;
    private DissertationListAdapter mAdapter;
    private int mPage_size;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dissertation_list;
    }

    @Override
    protected void initView() {
        mToolbarTitle.setText("精彩专题");
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new DissertationListAdapter(R.layout.adapter_dissertation_list, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.searchDissertation(page, false);
    }

    @OnClick(R.id.toolbar_left_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void handlerList(DissertationListEntity dissertationListEntities, boolean isRefresh) {
        mPage_size = dissertationListEntities.getPage_count();
        mList = dissertationListEntities.getDissertationInfo();
        if (isRefresh){
            mAdapter.setNewData(mList);
        }else {
            mAdapter.addData(mList);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_size) {
            page++;
            mPresenter.searchDissertation(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.searchDissertation(page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DissertationListEntity.DissertationInfoBean> data = adapter.getData();

        Intent intent = new Intent(this, DissertationActivity.class);
        intent.putExtra("id", data.get(position).getDissertation_id()+"");
        startActivity(intent);
    }
}
