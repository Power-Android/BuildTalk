package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleListAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.CircleListEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CircleListActivity extends BaseActivity<CircleListPresenter> implements CircleListContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

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
        mCircleListAdapter = new CircleListAdapter(R.layout.adapter_circle_list, mCircleInfo, mPresenter.mDataManager);
        mRecyclerView.setAdapter(mCircleListAdapter);
        mCircleListAdapter.setOnItemClickListener(this);
        mCircleListAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleList(page, false);
    }

    @Override
    public void handlerCircleList(CircleListEntity circleListEntity, boolean isRefresh) {
        mPage_count = circleListEntity.getPage_count();
        mCircleInfo = circleListEntity.getCircleInfo();
        if (isRefresh) {
            mCircleListAdapter.setNewData(mCircleInfo);
        } else {
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

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LoginHelper.login(this, mPresenter.mDataManager, () -> {
            List<CircleListEntity.CircleInfoBean> mCircleInfo = baseQuickAdapter.getData();
            mPresenter.attention(mCircleInfo.get(i).getUser_id(), mCircleInfo, i);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<CircleListEntity.CircleInfoBean> mCircleInfo = baseQuickAdapter.getData();
        Intent intent = new Intent(CircleListActivity.this, CircleManDetailActivity.class);
        intent.putExtra("user_id", mCircleInfo.get(i).getUser_id() + "");
        startActivity(intent);
    }

    @Override
    public void handlerAttrntion(BaseResponse<IEntity> baseResponse, List<CircleListEntity.CircleInfoBean> mList, int i) {
        int countAttention = mList.get(i).getCountAttention();
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())) {
            mList.get(i).setCountAttention(++countAttention);
            mList.get(i).setIs_attention(1);
        } else {
            if (countAttention > 1){
                mList.get(i).setCountAttention(--countAttention);
            }
            mList.get(i).setIs_attention(0);
        }
        mCircleListAdapter.notifyItemChanged(i);
    }
}
