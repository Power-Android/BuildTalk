package com.bjjy.buildtalk.contains.talk;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.TalkAdapter;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.contains.main.SearchActivity;
import com.bjjy.buildtalk.entity.TalkEntity;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author power
 * @date 2019/4/26 4:35 PM
 * @project BuildTalk
 * @description:
 */
public class TalkFragment extends BaseFragment<TalkPresnter> implements TalkContract.View, OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.talk_recyclerView)
    RecyclerView mTalkRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<TalkEntity> mTalkEntityList = new ArrayList<>();
    private TalkAdapter mTalkAdapter;

    public static TalkFragment newInstance() {
        return new TalkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void initView() {
        mToolbarTitle.setText(R.string.talk);
        mRefreshLayout.setOnRefreshListener(this);
        mTalkRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mTalkAdapter = new TalkAdapter(mTalkEntityList);
        mTalkRecyclerView.setAdapter(mTalkAdapter);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.talk_header_view,null);
        mTalkAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(v -> startActivity(new Intent(mContext, SearchActivity.class)));
        mTalkAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.talkType(mTalkEntityList);
    }

    @Override
    public void handlerTalkType(List<TalkEntity> talkEntityList) {
        mTalkAdapter.setNewData(talkEntityList);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        switch (view.getId()){
            case R.id.master_all_tv://行业大咖-查看全部
                startActivity(new Intent(mContext, MasterListActivity.class));
                break;
            case R.id.master_change_ll://行业大咖-换一换
                ImageView masterChangeIv = view.findViewById(R.id.master_change_iv);
                AnimatorUtils.setRotateAnimation(masterChangeIv);
                view.setClickable(false);
                new Handler().postDelayed(() -> {
//                    HOT_TOPTIC_PAGE++;
//                    mPresenter.discoverToptic();
                    view.setClickable(true);
                }, 2000);
                break;
            case R.id.circle_all_tv://人气圈主-查看全部
                startActivity(new Intent(mContext, CircleListActivity.class));
                break;
        }
    }
}
