package com.bjjy.buildtalk.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.TransactionAdapter;
import com.bjjy.buildtalk.adapter.TransactionTabAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.bjjy.buildtalk.entity.TransactionTabEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TransactionActivity extends BaseActivity<TransactionPresenter> implements TransactionContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.tab_recycler)
    RecyclerView mTabRecycler;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_rl)
    RelativeLayout mEmptyRl;

    private List<TransactionTabEntity> mTabList = new ArrayList<>();
    private TransactionTabAdapter mTabAdapter;

    private List<AleadyBuyEntity> mList = new ArrayList<>();
    private TransactionAdapter mTransactionAdapter;

    private String type = "0";
    private String mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mTitle = getIntent().getStringExtra("title");
        if (TextUtils.isEmpty(mTitle)){
            mToolbarTitle.setText(R.string.transaction_list);
        }else {
            mToolbarTitle.setText(mTitle);
        }

        mTabRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTabAdapter = new TransactionTabAdapter(R.layout.adapter_transaction_tab, mTabList);
        mTabRecycler.setAdapter(mTabAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTransactionAdapter = new TransactionAdapter(R.layout.adapter_transaction_layout, mList);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mTransactionAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.setTab();
        mPresenter.setRecord(type,mTitle);
    }

    @Override
    public void handlerTab(List<TransactionTabEntity> list) {
        mTabAdapter.setNewData(list);
        mTabAdapter.setOnItemClickListener((baseQuickAdapter, view, position) -> {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSelected(false);
            }
            list.get(position).setSelected(true);
            mTabAdapter.notifyDataSetChanged();
            switch (position) {
                case 0:
                    mPresenter.setRecord("0", mTitle);
                    break;
                case 1:
                    mPresenter.setRecord("2", mTitle);
                    break;
                case 2:
                    mPresenter.setRecord("1", mTitle);
                    break;
            }
        });
    }

    @Override
    public void handlerList(List<AleadyBuyEntity> list) {
        if (list.size() > 0){
            mEmptyRl.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTransactionAdapter.setNewData(list);
        }else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyRl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

    }
}
