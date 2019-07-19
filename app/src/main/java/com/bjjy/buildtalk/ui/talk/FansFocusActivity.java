package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.FansFocusAdapter;
import com.bjjy.buildtalk.adapter.FocusAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FansFocusActivity extends BaseActivity<FansFocusPresenter> implements FansFocusContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    private String mUser_id;
    private String mName;
    private String mType;
    private int page = 1;
    private List<FansFocusEntity.MyFansInfoBean> mMyFansInfo = new ArrayList<>();
    private int mPage_count;
    private FansFocusAdapter mFansFocusAdapter;
    private List<FansFocusEntity.AttentionInfoBean> mAttentionInfoBeans = new ArrayList<>();
    private FocusAdapter mFocusAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fans_focus;
    }

    @Override
    protected void initView() {
        mUser_id = getIntent().getStringExtra("user_id");
        mName = getIntent().getStringExtra("name");
        mType = getIntent().getStringExtra("type");
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        if ("fans".equals(mType)){
            mToolbarTitle.setText(mName + "的粉丝");
        }else {
            mToolbarTitle.setText(mName + "的关注");
        }
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if ("fans".equals(mType)){
            mFansFocusAdapter = new FansFocusAdapter(R.layout.adapter_circle_list, mMyFansInfo, mPresenter.mDataManager.getUser().getUser_id());
            mRecyclerView.setAdapter(mFansFocusAdapter);
            mFansFocusAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
                List<FansFocusEntity.MyFansInfoBean> mMyFansInfo = baseQuickAdapter.getData();
                LoginHelper.login(FansFocusActivity.this, mPresenter.mDataManager, () -> mPresenter.attention(mMyFansInfo.get(i).getUser_id(), mMyFansInfo, i));
            });
            mFansFocusAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
                List<FansFocusEntity.MyFansInfoBean> data = baseQuickAdapter.getData();
                if (data.get(i).getIs_author() == 0){
                    Intent intent = new Intent(FansFocusActivity.this, CircleManDetailActivity.class);
                    intent.putExtra("user_id", data.get(i).getUser_id() + "");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(FansFocusActivity.this, MasterDetailActivity.class);
                    intent.putExtra("user_id", data.get(i).getUser_id() + "");
                    startActivity(intent);
                }
            });
        }else {
            mFocusAdapter = new FocusAdapter(R.layout.adapter_circle_list, mAttentionInfoBeans, mPresenter.mDataManager.getUser().getUser_id());
            mRecyclerView.setAdapter(mFocusAdapter);
            mFocusAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
                List<FansFocusEntity.AttentionInfoBean> mAttentionInfoBeans = baseQuickAdapter.getData();
                LoginHelper.login(FansFocusActivity.this, mPresenter.mDataManager, () -> mPresenter.attention1(mAttentionInfoBeans.get(i).getAttention_user(), mAttentionInfoBeans, i));
            });
            mFocusAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
                List<FansFocusEntity.AttentionInfoBean> data = baseQuickAdapter.getData();
                if (data.get(i).getIs_author() == 0){
                    Intent intent = new Intent(FansFocusActivity.this, CircleManDetailActivity.class);
                    intent.putExtra("user_id", data.get(i).getAttention_user() + "");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(FansFocusActivity.this, MasterDetailActivity.class);
                    intent.putExtra("user_id", data.get(i).getAttention_user() + "");
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void initEventAndData() {
        if ("fans".equals(mType)){
            mPresenter.myFans(mUser_id, page, false);
        }else {
            mPresenter.myAttention(mUser_id, page, false);
        }
    }

    @Override
    public void handlerMyFans(FansFocusEntity fansFocusEntity, boolean isRefresh) {
        mPage_count = fansFocusEntity.getPage_count();
        mMyFansInfo = fansFocusEntity.getMyFansInfo();
        if (isRefresh){
            mFansFocusAdapter.setNewData(mMyFansInfo);
        }else {
            mFansFocusAdapter.addData(mMyFansInfo);
        }
    }

    @Override
    public void handlerMyAttention(FansFocusEntity fansFocusEntity, boolean isRefresh) {
        mPage_count = fansFocusEntity.getPage_count();
        mAttentionInfoBeans = fansFocusEntity.getAttentionInfo();
        if (isRefresh){
            mFocusAdapter.setNewData(mAttentionInfoBeans);
        }else {
            mFocusAdapter.addData(mAttentionInfoBeans);
        }
    }

    @Override
    public void handlerAttrntion(BaseResponse<IEntity> baseResponse, List<FansFocusEntity.MyFansInfoBean> mMyFansInfo, int i) {
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())){
            mMyFansInfo.get(i).setIs_attention(1);
        }else {
            mMyFansInfo.get(i).setIs_attention(0);
        }
        mFansFocusAdapter.notifyItemChanged(i);
        EventBus.getDefault().post(new RefreshEvent(Constants.FANS_REFRESH));
    }

    @Override
    public void handlerAttrntion1(BaseResponse<IEntity> baseResponse, List<FansFocusEntity.AttentionInfoBean> mAttentionInfoBeans, int i) {
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())){
            mAttentionInfoBeans.get(i).setIs_attention(1);
            mFocusAdapter.notifyItemChanged(i);
        }else {
            if (mUser_id.equals(mPresenter.mDataManager.getUser().getUser_id())){
                mAttentionInfoBeans.get(i).setIs_attention(0);
                mAttentionInfoBeans.remove(i);
                mFocusAdapter.notifyDataSetChanged();
            }else {
                mAttentionInfoBeans.get(i).setIs_attention(0);
                mFocusAdapter.notifyItemChanged(i);
            }
        }
        EventBus.getDefault().post(new RefreshEvent(Constants.FANS_REFRESH));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            if ("fans".equals(mType)){
                mPresenter.myFans(mUser_id, page, false);
            }else {
                mPresenter.myAttention(mUser_id, page, false);
            }
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if ("fans".equals(mType)){
            mPresenter.myFans(mUser_id, page, true);
        }else {
            mPresenter.myAttention(mUser_id, page, true);
        }
        refreshLayout.finishRefresh();
    }
}
