package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.MaseterCollectAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.CollectEntity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterCollectActivity extends BaseActivity<MasterCollectPresenter> implements MasterCollectContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    private String mName;
    private String mUser_id;
    private List<CollectEntity.MyCollectInfoBean> mList = new ArrayList<>();
    private int page = 1;
    private int mPage_count;
    private MaseterCollectAdapter mCollectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_collect;
    }

    @Override
    protected void initView() {
        mUser_id = getIntent().getStringExtra("user_id");
        mName = getIntent().getStringExtra("name");
        mToolbarBack.setOnClickListener(v -> finish());
        if (TextUtils.isEmpty(mName)){
            mToolbarTitle.setText("我的收藏");
        }else {
            mToolbarTitle.setText(mName + "的收藏");
        }

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCollectAdapter = new MaseterCollectAdapter(mList);
        mRecyclerView.setAdapter(mCollectAdapter);
        mCollectAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.myCollect(mUser_id, page, false);
    }

    @Override
    public void handlerCollect(CollectEntity collectEntity, boolean isRefresh) {
        mPage_count = collectEntity.getPage_count();
        mList = collectEntity.getMyCollectInfo();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getTheme_image().size() > 0 ||
                    mList.get(i).getTheme_pdf().size() > 0 ||
                    mList.get(i).getTheme_video().size() > 0){
                mList.get(i).setItemType(MaseterCollectAdapter.BODY_IMAGE);
            }else {
                mList.get(i).setItemType(MaseterCollectAdapter.BODY_TEXT);
            }
        }
        if (isRefresh){
            mCollectAdapter.setNewData(mList);
        }else {
            mCollectAdapter.addData(mList);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.myCollect(mUser_id, page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.myCollect(mUser_id, page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<CollectEntity.MyCollectInfoBean> mList = baseQuickAdapter.getData();
        if (mList.get(i).getTheme_status() == 1){
            Intent intent = new Intent(this, TopticDetailActivity.class);
            intent.putExtra("title", mList.get(i).getCircle_name());
            intent.putExtra("theme_id", mList.get(i).getTheme_id()+"");
            intent.putExtra("circle_id", "");
            startActivity(intent);
        }else {
            ToastUtils.showShort("该主题已被删除");
        }
    }
}
