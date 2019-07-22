package com.bjjy.buildtalk.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.EveryTalkListAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EveryTalkListActivity extends BaseActivity<EveryTalkListPresenter> implements EveryTalkContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.introduce_iv)
    ImageView mIntroduceIv;
    @BindView(R.id.jieshao_layout)
    NestedScrollView mJieshaoLayout;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.edu_tv)
    TextView mEduTv;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.desc_tv)
    TextView mDescTv;
    @BindView(R.id.view2)
    TextView mView2;
    @BindView(R.id.received_tv)
    TextView mReceivedTv;
    @BindView(R.id.num_tv)
    TextView mNumTv;

    private EveryTalkListAdapter mTalkListAdapter;
    private int page = 1;
    private int mPage_count = 1;
    private List<EveryTalkListEntity.NewsInfoBean> mNewsInfoBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_every_talk_list;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, true);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mTalkListAdapter = new EveryTalkListAdapter(R.layout.adapter_every_talk_list, mNewsInfoBeanList);
        mRecyclerView.setAdapter(mTalkListAdapter);
        mTalkListAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.talkList(page, false);
    }

    @Override
    public void handlerTalkList(EveryTalkListEntity everyTalkListEntity, boolean isRefresh) {
        mPage_count = everyTalkListEntity.getPage_count();
        int countupdate = everyTalkListEntity.getAuthorInfo().getCountupdate();
        mNumTv.setText("已更新至" + countupdate + "期");
        introduce(everyTalkListEntity.getAuthorInfo());
        mNewsInfoBeanList = everyTalkListEntity.getNewsInfo();
        if (isRefresh) {
            mTalkListAdapter.setNewData(mNewsInfoBeanList);
        } else {
            mTalkListAdapter.addData(mNewsInfoBeanList);
        }
    }

    private void introduce(EveryTalkListEntity.AuthorInfoBean authorInfo) {
        mNameTv.setText(authorInfo.getAuthor_name());
        mEduTv.setText(authorInfo.getEducation());
        List<String> author_desc = authorInfo.getAuthor_desc();
        mFlowLayout.setAdapter(new TagAdapter<String>(author_desc) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                if ("".equals(s)) {
                    return new View(EveryTalkListActivity.this);
                }
                TextView tv = (TextView) LayoutInflater.from(EveryTalkListActivity.this)
                        .inflate(R.layout.tag_layout_tv, parent, false);
                tv.setText(s);
                return tv;
            }
        });
        mDescTv.setText(authorInfo.getRemark());
        mReceivedTv.setText(authorInfo.getReceived());
    }

    @OnClick({R.id.back_iv, R.id.introduce_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.introduce_iv:
                if (mJieshaoLayout.getVisibility() == View.GONE) {
                    mIntroduceIv.setImageDrawable(getResources().getDrawable(R.drawable.every_talk_arrow_top));
                    mRefreshLayout.setEnableRefresh(false);
                    mRefreshLayout.setEnableLoadMore(false);
                    mNumTv.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    mJieshaoLayout.setVisibility(View.VISIBLE);
                } else {
                    mIntroduceIv.setImageDrawable(getResources().getDrawable(R.drawable.every_talk_arrow_bottom));
                    mRefreshLayout.setEnableRefresh(true);
                    mRefreshLayout.setEnableLoadMore(true);
                    mJieshaoLayout.setVisibility(View.GONE);
                    mNumTv.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.talkList(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.talkList(page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<EveryTalkListEntity.NewsInfoBean> data = adapter.getData();
        Intent intent = new Intent(EveryTalkListActivity.this, EveryTalkDetailActivity.class);
        intent.putExtra("article_id", data.get(position).getArticle_id() + "");
        startActivity(intent);
    }

}
