package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.MasterListAdapter;
import com.bjjy.buildtalk.adapter.SearchResultAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterListActivity extends BaseActivity<MasterListPresenter> implements MasterListContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private MasterListAdapter mMasterListAdapter;
    private int page = 1;
    private int mPage_count;
    private List<MasterListEntity.MasterInfoBean> mMasterInfo = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_list;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.industry_higher_ups);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMasterListAdapter = new MasterListAdapter(R.layout.adapter_search_result, mMasterInfo);
        mRecyclerView.setAdapter(mMasterListAdapter);
        mMasterListAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.masterList(page, false);
    }

    @Override
    public void handlerMasterList(MasterListEntity masterListEntity, boolean isRefresh) {
        mPage_count = masterListEntity.getPage_count();
        mMasterInfo = masterListEntity.getMasterInfo();
        if (isRefresh){
            mMasterListAdapter.setNewData(mMasterInfo);
        }else {
            mMasterListAdapter.addData(mMasterInfo);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.masterList(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.masterList(page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<MasterListEntity.MasterInfoBean> mMasterInfo = baseQuickAdapter.getData();
        Intent intent = new Intent(this, MasterDetailActivity.class);
        intent.putExtra("user_id", mMasterInfo.get(i).getUser_id() + "");
        startActivity(intent);
    }
}
