package com.bjjy.buildtalk.ui.mine;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.VersionRecordAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        mPresenter.versionRecord(getAppVersionName(this));
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName=null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @Override
    public void handlerVersionRecord(List<VersionRecordEntity> versionRecordEntities) {
        mAdapter.setNewData(versionRecordEntities);
    }
}
