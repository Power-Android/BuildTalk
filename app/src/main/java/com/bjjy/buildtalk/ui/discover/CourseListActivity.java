package com.bjjy.buildtalk.ui.discover;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CourseListAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseListActivity extends BaseActivity<CourseListPresenter> implements CourseListContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<CourseEntity.CircleInfoBean> mList = new ArrayList<>();
    private CourseListAdapter mCourseListAdapter;
    private int page = 1;
    private int mPage_count = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_list;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(getString(R.string.course));

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseListAdapter = new CourseListAdapter(R.layout.adapter_course_list, mList);
        mRecyclerView.setAdapter(mCourseListAdapter);
        mCourseListAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.courseList(page, false);
    }

    @Override
    public void handlerCourseList(CourseEntity courseEntities, boolean isRefresh) {
        mPage_count = courseEntities.getPage_count();
       mList = courseEntities.getCircleInfo();
        if (isRefresh) {
            mCourseListAdapter.setNewData(mList);
        } else {
            mCourseListAdapter.addData(mList);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.courseList(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.courseList(page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<CourseEntity.CircleInfoBean> mList = adapter.getData();
        Intent intent = new Intent(this, CourseCircleActivity.class);
        intent.putExtra("circle_id", String.valueOf(mList.get(position).getCircle_id()));
        startActivity(intent);
    }
}
