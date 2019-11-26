package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.SearchResultAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.ClearEditText;
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
import butterknife.OnClick;

public class TalkSearchActivity extends BaseActivity<TalkSearchPresenter> implements TalkSearchContract.View, TextView.OnEditorActionListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener, View.OnFocusChangeListener {

    @BindView(R.id.search_et)
    ClearEditText mSearchEt;
    @BindView(R.id.cancle_tv)
    TextView mCancleTv;
    @BindView(R.id.delete_iv)
    ImageView mDeleteIv;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.search_rl)
    LinearLayout mSearchRl;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.emptyView)
    RelativeLayout mEmptyView;

    private List<SearchResultEntity.AuthorInfoBean> mList = new ArrayList<>();
    private SearchResultAdapter mSearchResultAdapter;
    private int page = 1, pageCount = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mSearchEt.setHint(R.string.search_name);
        mSearchEt.setOnEditorActionListener(this);
        mSearchEt.setOnFocusChangeListener(this);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultAdapter = new SearchResultAdapter(R.layout.adapter_search_result_talk, mList);
        mRecyclerView.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getSearchData();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mSearchEt.setFocusable(false);
            if (TextUtils.isEmpty(mSearchEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入搜索内容");
                return true;
            }
            gotoSearchResult(page, mSearchEt.getText().toString().trim(), true);
            KeyboardUtils.hideSoftInput(this);
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mPresenter.getSearchData();
            mRefreshLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mSearchRl.setVisibility(View.VISIBLE);
        } else {
            mSearchRl.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    private void gotoSearchResult(int page, String content, boolean isRefresh) {
        mPresenter.addHistoryData(page, content, isRefresh);
    }

    @Override
    public void handlerResultSearchList(SearchResultEntity searchResultEntity, boolean isRefresh) {
        pageCount = searchResultEntity.getPage_count();
        mList = searchResultEntity.getAuthorInfo();
        if (isRefresh) {
            mSearchResultAdapter.setNewData(mList);
        } else {
            mSearchResultAdapter.addData(mList);
        }
        if (searchResultEntity.getAuthorInfo().size() > 0) {
            mSearchRl.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            showEmpty();
        }
    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        mSearchRl.setVisibility(View.VISIBLE);
        mFlowLayout.setAdapter(new TagAdapter<HistoryData>(historyDataList) {
            @Override
            public View getView(FlowLayout parent, int position, HistoryData historyDataList) {
                TextView tv = (TextView) LayoutInflater.from(TalkSearchActivity.this)
                        .inflate(R.layout.search_flow_layout_tv, parent, false);
                if (historyDataList != null) {
                    tv.setText(historyDataList.getData());
                }
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
            mSearchEt.setFocusable(false);
            page = 1;
            gotoSearchResult(page, historyDataList.get(position1).getData(), true);
            mSearchEt.setText(historyDataList.get(position1).getData());
            mSearchEt.setSelection(historyDataList.get(position1).getData().length());//将光标追踪到内容的最后
            KeyboardUtils.hideSoftInput(this);
            return true;
        });
    }

    @OnClick({R.id.cancle_tv, R.id.delete_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancle_tv:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
            case R.id.delete_iv:
                mPresenter.mDataManager.clearAllHistoryData();
                mPresenter.getSearchData();
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < pageCount) {
            page++;
            gotoSearchResult(page, mSearchEt.getText().toString().trim(), false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        gotoSearchResult(page, mSearchEt.getText().toString().trim(), true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<SearchResultEntity.AuthorInfoBean> mList = baseQuickAdapter.getData();
        if (mList.get(i).getIs_author() == 0) {
            Intent intent = new Intent(this, CircleManDetailActivity.class);
            intent.putExtra("user_id", mList.get(i).getUser_id() + "");
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MasterDetailActivity.class);
            intent.putExtra("user_id", mList.get(i).getUser_id() + "");
            startActivity(intent);
        }

    }
}
