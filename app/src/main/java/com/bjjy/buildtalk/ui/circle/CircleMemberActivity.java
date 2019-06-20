package com.bjjy.buildtalk.ui.circle;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleMemberAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CircleMemberActivity extends BaseActivity<CircleMemberPresenter> implements CircleMemberContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<MemberEntity.CircleUserBean> mList = new ArrayList<>();
    private String mCircle_id;
    private int page = 1;
    private CircleMemberAdapter mMemberAdapter;
    private int mPage_count;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_member;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.circle_member);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMemberAdapter = new CircleMemberAdapter(R.layout.adapter_circle_member, mList);
        mRecyclerView.setAdapter(mMemberAdapter);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleUser(mCircle_id, page, false);
    }

    @Override
    public void handlerCircleUser(MemberEntity memberEntity, boolean isRefresh) {
        mPage_count = memberEntity.getPage_count();
        if (isRefresh){
            mMemberAdapter.setNewData(memberEntity.getCircleUser());
        }else {
            mMemberAdapter.addData(memberEntity.getCircleUser());
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.circleUser(mCircle_id, page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.circleUser(mCircle_id, page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

}
