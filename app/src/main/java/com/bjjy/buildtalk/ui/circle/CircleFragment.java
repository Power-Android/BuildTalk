package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.NetworkUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author power
 * @date 2019/4/26 4:33 PM
 * @project BuildTalk
 * @description: 圈子 模块
 */
public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleContract.View, OnRefreshListener, BaseQuickAdapter.OnItemClickListener, OnRefreshLoadMoreListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.circle_recyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    @BindView(R.id.noNetView)
    RelativeLayout mNoNetView;

    private List<CircleEntity.CircleInfoBean> circle_list = new ArrayList<>();
    private CircleAdapter mCircleAdapter;

    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;
    private int page = 1;
    private int mPage_count = 1;
    private View mFooterView;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.TOPTIC_REFRESH_ALL)) {
            onRefresh(mRefreshLayout);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.QUIT_CIRCLE)) {
            onRefresh(mRefreshLayout);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.INFO_REFRESH)) {
            onRefresh(mRefreshLayout);
        }
    }

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mToolbarBack.setVisibility(View.GONE);
        mToolbarTitle.setText(R.string.circle);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mCircleRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mCircleAdapter = new CircleAdapter(circle_list);
        mCircleRecyclerView.setAdapter(mCircleAdapter);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.circle_header_view, null);
        mCircleAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(v -> startActivity(new Intent(mContext, CircleSearchActivity.class)));
        mCircleAdapter.setFooterViewAsFlow(true);
        mFooterView = LayoutInflater.from(mContext).inflate(R.layout.circle_footer_view, null);
        mCircleAdapter.addFooterView(mFooterView);
        mFooterView.setOnClickListener(v -> LoginHelper.login(mContext, mPresenter.mDataManager, () -> createCircle()));
        mCircleAdapter.setOnItemClickListener(this);
        mTvReload.setOnClickListener(v -> onRefresh(mRefreshLayout));
    }

    @Override
    protected void initEventAndData() {
        if (!NetworkUtils.isConnected()){
            mRefreshLayout.setVisibility(View.GONE);
            mNoNetView.setVisibility(View.VISIBLE);
        }else {
            mNoNetView.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mPresenter.circleList(page, false);
        }
    }

    @Override
    public void handlerCircleList(CircleEntity circleEntity, boolean isRefresh) {
        mPage_count = circleEntity.getPage_count();
        circle_list = circleEntity.getCircleInfo();
        if (isRefresh) {
            mCircleAdapter.setNewData(circle_list);
        } else {
            mCircleAdapter.addData(circle_list);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (!NetworkUtils.isConnected()){
            mRefreshLayout.setVisibility(View.GONE);
            mNoNetView.setVisibility(View.VISIBLE);
        }else {
            mNoNetView.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mPresenter.circleList(page, true);
        }
        refreshLayout.finishRefresh(1500);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.circleList(page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    private void createCircle() {
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.bottom_sheet_dialog);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            mView = getLayoutInflater().inflate(R.layout.dialog_circle_agreenment, null);
            mBottomSheetDialog.setContentView(mView);
            mBehavior = BottomSheetBehavior.from((View) mView.getParent());
            mBehavior.setSkipCollapsed(true);
//            int peekHeight = getResources().getDisplayMetrics().heightPixels;
            //设置默认弹出高度为屏幕的0.4倍
//            mBehavior.setPeekHeight((int)(0.4 * peekHeight));
        }
        mBottomSheetDialog.show();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CheckBox checkBox = mView.findViewById(R.id.checkbox);
        TextView agreement = mView.findViewById(R.id.agreement);
        TextView next = mView.findViewById(R.id.next);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                next.setBackground(getResources().getDrawable(R.drawable.shape_blue_5button));
                next.setClickable(true);
            } else {
                next.setBackground(getResources().getDrawable(R.drawable.shape_blue_light_5button));
                next.setClickable(false);
            }
        });
        agreement.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            Intent intent = new Intent(mContext, CircleAgreementActvity.class);
            intent.putExtra("type", "qzgz");
            intent.putExtra("url", "https://jt.chinabim.com/qz.html");
            startActivity(intent);
        });
        next.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            startActivity(new Intent(mContext, CreateCircleActivity.class));
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        List<CircleEntity.CircleInfoBean> list = baseQuickAdapter.getData();
        if (2 == list.get(position).getType()) {
            Intent intent = new Intent(mContext, CourseCircleActivity.class);
            intent.putExtra("circle_id", String.valueOf(list.get(position).getCircle_id()));
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, TopticCircleActivity.class);
            intent.putExtra("circle_id", String.valueOf(list.get(position).getCircle_id()));
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
