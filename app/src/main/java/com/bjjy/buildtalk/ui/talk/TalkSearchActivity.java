package com.bjjy.buildtalk.ui.talk;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.SearchResultAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.greendao.HistoryData;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.ClearEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TalkSearchActivity extends BaseActivity<TalkSearchPresenter> implements TalkSearchContract.View, TextView.OnEditorActionListener {

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

    private List<String> mList = new ArrayList<>();
    private SearchResultAdapter mSearchResultAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mSearchEt.setHint(R.string.search_name);
        mSearchEt.setOnEditorActionListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultAdapter = new SearchResultAdapter(R.layout.adapter_search_result, mList);
        mRecyclerView.setAdapter(mSearchResultAdapter);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getSearchData();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            if (TextUtils.isEmpty(mSearchEt.getText().toString().trim())){
                ToastUtils.showShort("请输入搜索内容");
                return true;
            }
            gotoSearchResult(mSearchEt.getText().toString().trim());
            KeyboardUtils.hideSoftInput(this);
        }
        return false;
    }

    private void gotoSearchResult(String content) {
        mPresenter.addHistoryData(content);
    }

    @Override
    public void handlerResultSearchList(List<String> list) {
        mSearchResultAdapter.setNewData(list);
        mSearchRl.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        if (historyDataList.size() == 0){
            mSearchRl.setVisibility(View.GONE);
            return;
        }
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
            gotoSearchResult(historyDataList.get(position1).getData());
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


}