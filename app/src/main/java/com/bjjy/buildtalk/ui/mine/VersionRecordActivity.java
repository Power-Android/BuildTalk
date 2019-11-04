package com.bjjy.buildtalk.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.VersionRecordAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VersionRecordActivity extends BaseActivity<VersionRecordPresenter> implements VersionRecordContarct.View {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<VersionRecordEntity> mList = new ArrayList<>();
    private VersionRecordAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_version_record;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText("版本记录");
    }

    @Override
    protected void initEventAndData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VersionRecordAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.versionRecord();
    }

    @Override
    public void handlerVersionRecord(List<VersionRecordEntity> versionRecordEntities) {
        mAdapter.setNewData(versionRecordEntities);
    }
}
