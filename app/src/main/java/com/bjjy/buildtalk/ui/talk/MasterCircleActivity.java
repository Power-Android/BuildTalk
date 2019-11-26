package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleSearchResultAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterCircleActivity extends BaseActivity<MasterCirclePresenter> implements MasterCircleContract.View, OnRefreshLoadMoreListener, ViewPager.OnPageChangeListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    private String mName;
    private String mUser_id;
    private int create_page = 1, join_page = 1;
    private CircleSearchResultAdapter mAdapter, mAdapter1;
    private List<SearchResultEntity.CircleInfoBean> mList = new ArrayList<>();
    private List<SearchResultEntity.CircleInfoBean> mList1 = new ArrayList<>();
    private int create_Page_count, join_page_count;
    private int page_Position = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_circle;
    }

    @Override
    protected void initView() {
        mUser_id = getIntent().getStringExtra("user_id");
        mName = getIntent().getStringExtra("name");
        mToolbarBack.setOnClickListener(v -> finish());
        if (TextUtils.isEmpty(mName)){
            mToolbarTitle.setText("我的圈子");
        }else {
            mToolbarTitle.setText(mName + "的圈子");
        }
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.tabData();
    }

    @Override
    public void handlerTab(List<String> titleList, List<View> views) {
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(titleList, views);
        mViewPager.setAdapter(viewPagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);

        RecyclerView create_recyclerView = views.get(0).findViewById(R.id.recycler_view);
        create_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CircleSearchResultAdapter(R.layout.adapter_circle_search_result, mList);
        create_recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            List<SearchResultEntity.CircleInfoBean> mList = baseQuickAdapter.getData();
            if (TextUtils.equals("1", mList.get(i).getType())){
                Intent intent = new Intent(MasterCircleActivity.this, TopticCircleActivity.class);
                intent.putExtra("circle_id", mList.get(i).getCircle_id()+"");
                startActivity(intent);
            }else {
                Intent intent = new Intent(MasterCircleActivity.this, CourseCircleActivity.class);
                intent.putExtra("circle_id", mList.get(i).getCircle_id()+"");
                startActivity(intent);
            }
        });

        RecyclerView join_recyclerView = views.get(1).findViewById(R.id.recycler_view);
        join_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter1 = new CircleSearchResultAdapter(R.layout.adapter_circle_search_result, mList1);
        join_recyclerView.setAdapter(mAdapter1);
        mAdapter1.setOnItemClickListener(this);

        mPresenter.createList(mUser_id, create_page, false);
        mPresenter.joinList(mUser_id, join_page, false);
    }

    @Override
    public void handlerCreate(SearchResultEntity resultEntity, boolean isRefresh) {
        create_Page_count = resultEntity.getPage_count();
        mList = resultEntity.getCircleInfo();
        if (isRefresh){
            mAdapter.setNewData(mList);
        }else {
            mAdapter.addData(mList);
        }
    }

    @Override
    public void handlerJoin(SearchResultEntity resultEntity, boolean isRefresh) {
        join_page_count = resultEntity.getPage_count();
        mList1 = resultEntity.getCircleInfo();
        if (isRefresh){
            mAdapter1.setNewData(mList1);
        }else {
            mAdapter1.addData(mList1);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (0 == page_Position){
            if (create_page < create_Page_count) {
                create_page++;
                mPresenter.createList(mUser_id, create_page, false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }else {
            if (join_page < join_page_count) {
                join_page++;
                mPresenter.joinList(mUser_id, join_page, false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (0 == page_Position){
            create_page = 1;
            mPresenter.createList(mUser_id, create_page, true);
        }else {
            join_page = 1;
            mPresenter.joinList(mUser_id, join_page, true);
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        page_Position = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<SearchResultEntity.CircleInfoBean> mList = baseQuickAdapter.getData();
        if (TextUtils.equals("1", mList.get(i).getType())){
            Intent intent = new Intent(MasterCircleActivity.this, TopticCircleActivity.class);
            intent.putExtra("circle_id", mList.get(i).getCircle_id()+"");
            startActivity(intent);
        }else {
            Intent intent = new Intent(MasterCircleActivity.this, CourseCircleActivity.class);
            intent.putExtra("circle_id", mList.get(i).getCircle_id()+"");
            startActivity(intent);
        }
    }
}
