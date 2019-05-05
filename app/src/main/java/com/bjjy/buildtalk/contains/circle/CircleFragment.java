package com.bjjy.buildtalk.contains.circle;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleAdapter;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author power
 * @date 2019/4/26 4:33 PM
 * @project BuildTalk
 * @description: 圈子 模块
 */
public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleContract.View, OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.circle_recyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<String> circle_list = new ArrayList<>();
    private CircleAdapter mCircleAdapter;

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView() {
        mToolbarTitle.setText(R.string.circle);
        mRefreshLayout.setOnRefreshListener(this);
        mCircleRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mCircleAdapter = new CircleAdapter(R.layout.adapter_circle_layout,circle_list);
        mCircleRecyclerView.setAdapter(mCircleAdapter);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.circle_header_view,null);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.circle_footer_view,null);
        mCircleAdapter.addHeaderView(headerView);
        mCircleAdapter.addFooterView(footerView);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleList(circle_list);
    }

    @Override
    public void handlerCircleList(List<String> circle_list) {
        mCircleAdapter.setNewData(circle_list);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000);
    }
}
